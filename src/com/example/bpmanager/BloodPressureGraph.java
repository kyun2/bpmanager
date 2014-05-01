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
	
	public GraphicalView execute(Context context) {
		// 라인 2개
		String[] titles = new String[] { "수축기", "이완기" };
		List<Date[]> dates = new ArrayList<Date[]>();
		List<double[]> values = new ArrayList<double[]>();		
		// 최대 30개까지.
		List<BloodPressure> bplist = BloodPressure.getBPsList("", 30, true);
		
		int bpsize = bplist.size();
		if (bpsize == 0)
			return null;
		
		int length = titles.length;
	    for (int i = 0; i < length; i++) {
	    	dates.add(new Date[bpsize]);
	    	for (int i2 = 0; i2 < bpsize; i2++)
	    	{
	    		dates.get(i)[i2] = new Date(100, 0, i2 + 1);
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
	    
	    setChartSettings(renderer, getName(), "월별", "혈압수치", dates.get(0)[0].getTime() - 24 * 60 * 60 * 1000,
	    		dates.get(0)[0].getTime() + 5 * 24 * 60 * 60 * 1000/*dates.get(0)[Math.min(bpsize-1, 4)].getTime()*/, 50, 200, Color.BLACK, Color.BLACK);
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
	      seriesRenderer.setChartValuesTextSize(40f);
	    }
	    return ChartFactory.getTimeChartView(context, buildDateDataset(titles, dates, values), renderer, "yyyy/MM/dd");
	}
}
