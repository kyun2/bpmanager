package com.example.bpmanager;

import java.util.ArrayList;
import java.util.Calendar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Pair;

public class MedicationInjectionGraph {
	
	private final int SAMPLE_COUNT = 30;
	
	public GraphicalView excute(Context context)
	{
		// 복약기록 정리
		Pair<Integer, Integer> counts = MainActivity.mMediHistData.setTookRatioDataList(SAMPLE_COUNT);
		if (counts.second == 0)
			return null;
		
		ArrayList<Float> data = MainActivity.mMediHistData.getDataList();
		ArrayList<Integer> parsedData = new ArrayList<Integer>();
		
		for (int i = 0; i < data.size(); i++)
		{
			if (data.get(i) == 0)
			{
				// trim check
				boolean no_value = true;
				for (int i2 = i + 1; i2 < data.size(); i2++)
				{
					if (data.get(i2) != 0)
					{
						no_value = false;
						break;
					}
				}
				if (no_value)
					break;
			}
			parsedData.add(Math.round(data.get(i) * 100f));
		}
		
		CategorySeries series = new CategorySeries("Bar Graph");
		int dataSize = parsedData.size();
		for (int i = 0; i < dataSize; i++)
		{
			series.add(parsedData.get(dataSize - 1 - i));
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());
		
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesSpacing((float)1); // gap between bar and number
		renderer.setColor(Color.YELLOW);
		renderer.setChartValuesTextAlign(Align.LEFT);
		renderer.setChartValuesTextSize(30f);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setChartTitle("복용성취율 현황");		
		mRenderer.setChartTitleTextSize(30f);
		
		mRenderer.setMarginsColor(Color.WHITE);
		mRenderer.setBackgroundColor(Color.WHITE);
		mRenderer.setZoomEnabled(false, false);
		mRenderer.setPanEnabled(true, false);
		mRenderer.setShowLegend(false);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setLabelsTextSize(20f);
		mRenderer.setAxisTitleTextSize(20f);
		mRenderer.setShowAxes(true);
		mRenderer.setBarWidth(20f);
		
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setGridColor(Color.BLACK);
		
		mRenderer.setXTitle("날짜");
		mRenderer.setXLabels(0);
		mRenderer.setXAxisMin(dataSize - 2);
		mRenderer.setXAxisMax(dataSize + 2);
		mRenderer.setXLabelsColor(Color.BLACK);
		mRenderer.setXRoundedLabels(true);
		for (int i = 1; i <= dataSize; i++)
		{
			Calendar today = Calendar.getInstance(); 
			today.add(Calendar.DATE, i - dataSize);			
			mRenderer.addXTextLabel(i, String.format("%04d/%02d/%02d", today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DAY_OF_MONTH)));
		}
		
		mRenderer.setYTitle("복용성취율(%)");
		mRenderer.setYAxisMin(0.0);
		mRenderer.setYAxisMax(100.0);
		mRenderer.setYLabels(5);
		mRenderer.setYLabelsColor(0, Color.BLACK);
		mRenderer.setShowGridX(true);
				
		return ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);
	}
}

