package com.example.bpmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;

public class BloodPressureGraph extends AbstractDemoChart {
	
	private final int SAMPLE_COUNT = 30;

	public String getName() {
		return "혈압 기록";
	}
	
	public String getDesc() {
		return "";
	}
	
	public List<BloodPressure> setupList(List<BloodPressure> data)
	{
		List<BloodPressure> list = new ArrayList<BloodPressure>();
		
		// Init
		for (int i = 0; i < SAMPLE_COUNT; i++)
		{
			list.add(new BloodPressure(0, 0, "0000/00/00"));
		}
		
		Calendar now = Calendar.getInstance();
		int now_y = now.get(Calendar.YEAR);
		int now_m = now.get(Calendar.MONTH);
		int now_d = now.get(Calendar.DAY_OF_MONTH);
		now.clear();
		now.set(now_y, now_m, now_d);
		for (int i = 0; i < data.size(); i++)
		{
			String[] time = data.get(i).getDatetime().split("/");
			int year = Integer.parseInt(time[0]);
			int month = Integer.parseInt(time[1]) - 1;
			int day = Integer.parseInt(time[2]);
			Calendar c = Calendar.getInstance();
			c.clear();
			c.set(year, month, day);
			
			long diffSec = (now.getTimeInMillis() - c.getTimeInMillis()) / 1000;
			int diffDay = (int)(diffSec / (60 * 60 * 24));
			
			list.set(diffDay, data.get(i));
		}
		
		// Interporation
		int pre_index = 0;
		int pre_sys = 0;
		int pre_dia = 0;
		int abort_index = 0;
		for (int i = 0; i < SAMPLE_COUNT; i++)
		{
			BloodPressure bp = list.get(i);
			if (bp.getSystolic() == 0 && bp.getDiastolic() == 0)
			{
				// get next value
				int next_index = 0;
				int next_sys = 0;
				int next_dia = 0;
				for (int i2 = i + 1; i2 < SAMPLE_COUNT; i2++)
				{
					next_sys = list.get(i2).getSystolic();
					next_dia = list.get(i2).getDiastolic();
					if (next_sys > 0 && next_dia > 0)
					{
						next_index = i2;
						break;
					}
				}
				if (next_sys == 0 && next_dia == 0)
				{
					next_index = i;
					next_sys = pre_sys;
					next_dia = pre_dia;
					abort_index = i;
					break;
				}
				
				// calculate
				if (i == next_index)
				{
					list.get(i).setSystolic(pre_sys);
					list.get(i).setDiastolic(pre_dia);
				}
				else
				{
					if (pre_sys == 0 && pre_dia == 0)
					{
						list.get(i).setSystolic(next_sys);
						list.get(i).setDiastolic(next_dia);
					}
					else
					{
						int inter_sys = Math.round(pre_sys + (float)(next_sys - pre_sys) / (float)(next_index - pre_index));
						int inter_dia = Math.round(pre_dia + (float)(next_dia - pre_dia) / (float)(next_index - pre_index));
						list.get(i).setSystolic(inter_sys);
						list.get(i).setDiastolic(inter_dia);
					}
				}
			}
			pre_index = i;
			pre_sys = list.get(i).getSystolic();
			pre_dia = list.get(i).getDiastolic();
		}
/*
		if (abort_index > 0)
		{
			for (int i = abort_index; i < SAMPLE_COUNT; i++)
			{
				list.remove(i);
			}
		}
		*/
		
		List<BloodPressure> ret = new ArrayList<BloodPressure>(abort_index);
		for (int i = 0; i < abort_index; i++)
		{
			ret.add(list.get(i));
		}
		
		return ret;
	}
	
	public GraphicalView execute(Context context) {
		// 라인 2개
		String[] titles = new String[] { "수축기", "이완기" };
		List<Date[]> dates = new ArrayList<Date[]>();
		List<double[]> values = new ArrayList<double[]>();		
		// 30일전 자료부터
		List<BloodPressure> bpdata = BloodPressure.getLastBPsList(SAMPLE_COUNT, false);
		
		if (bpdata.size() == 0)
			return null;

		List<BloodPressure> bplist = setupList(bpdata);
		int bpsize = bplist.size();		
		
		int length = titles.length;
	    for (int i = 0; i < length; i++) {
	    	dates.add(new Date[bpsize]);
	    	for (int i2 = 0; i2 < bpsize; i2++)
	    	{
	    		Calendar c = Calendar.getInstance();
	    		int year = c.get(Calendar.YEAR);
	    		int month = c.get(Calendar.MONTH);
	    		int day = c.get(Calendar.DAY_OF_MONTH);
	    		c.clear();
	    		c.set(year, month, day);
	    		c.add(Calendar.DAY_OF_MONTH, i2 - bpsize + 1);
	    		dates.get(i)[i2] = c.getTime();
	    	}
	    }
	    // 수축기
	    double[] sysval = new double[bpsize];
	    for (int i = 0; i < bpsize; i++)
	    {
	    	sysval[i] = bplist.get(i).getSystolic();
	    }
	    values.add(sysval);
	    // 이완기
	    double[] diaval = new double[bpsize];
	    for (int i = 0; i < bpsize; i++)
	    {
	    	diaval[i] = bplist.get(i).getDiastolic();
	    }
	    values.add(diaval);
	    length = values.get(0).length;

	    // 렌더러 세팅
		int[] colors = new int[] { Color.BLUE, Color.GREEN };
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT };
	    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);	    
	    
	    setChartSettings(renderer, getName(), "", "혈압수치", dates.get(0)[0].getTime() - 1 * 24 * 60 * 60 * 1000,
	    		dates.get(0)[0].getTime() + 3 * 24 * 60 * 60 * 1000, 50, 200, Color.BLACK, Color.BLACK);
	    renderer.setXLabels(0);
	    renderer.setYLabels(0);
	    renderer.setApplyBackgroundColor(true);
	    renderer.setMarginsColor(Color.WHITE);
	    renderer.setBackgroundColor(Color.LTGRAY);
	    renderer.setZoomEnabled(false, false);
	    renderer.setPanEnabled(true, false);
	    renderer.setShowAxes(true);
	    renderer.setAxisTitleTextSize(20f);
	    renderer.setChartTitleTextSize(30f);
	    renderer.setLegendTextSize(24f);
	    
	    BloodPressure recommendBP = BloodPressure.getRecommendBloodPressure();
	    renderer.addYTextLabel(recommendBP.getSystolic(), "목표수축혈압");
	    renderer.addYTextLabel(recommendBP.getDiastolic(), "목표이완혈압");
	    
	    renderer.setXRoundedLabels(false);
	    length = renderer.getSeriesRendererCount();
	    for (int i = 0; i < length; i++) {
	      SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
	      seriesRenderer.setDisplayChartValues(true);
	      seriesRenderer.setChartValuesTextSize(30f);
	    }
	    return ChartFactory.getTimeChartView(context, buildDateDataset(titles, dates, values), renderer, "yyyy/MM/dd");
	}
}
