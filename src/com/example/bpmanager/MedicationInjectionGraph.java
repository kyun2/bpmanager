package com.example.bpmanager;

import java.util.ArrayList;

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
import android.util.Pair;

public class MedicationInjectionGraph {
	
	public GraphicalView excute(Context context)
	{
		// 복약기록 정리
		Pair<Integer, Integer> counts = MainActivity.mMediHistData.setTookRatioDataList(30);
		if (counts.second == 0)
			return null;
		
		ArrayList<Float> data = MainActivity.mMediHistData.getDataList();
		ArrayList<Integer> parsedData = new ArrayList<Integer>();
		
		for (int i = 0; i < data.size(); i++)
		{
			parsedData.add(Math.round(data.get(i) * 100f));
		}
		
		CategorySeries series = new CategorySeries("Bar Graph");
		for (int i = 0; i < parsedData.size(); i++)
		{
			series.add(parsedData.get(i));
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series.toXYSeries());
		
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesSpacing((float)1); // gap between bar and number
		renderer.setColor(Color.YELLOW);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setChartTitle("복용성취율 현황");
		mRenderer.setChartTitleTextSize(20f);
		mRenderer.setXTitle("기간");
		mRenderer.setYTitle("복용성취율");
		mRenderer.setXLabels(0);
		mRenderer.setYLabels(0);
		mRenderer.setYAxisMin(0.0);
		mRenderer.setYAxisMax(100.0);
		mRenderer.setShowAxes(true);
		mRenderer.setShowGridX( true );
		//mRenderer.setBarWidth( (float) 30 );
		mRenderer.setBarSpacing((float)0.5);
		mRenderer.setShowLegend(false);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setMarginsColor(Color.WHITE);
		mRenderer.setBackgroundColor(Color.LTGRAY);
		mRenderer.setZoomEnabled(false, false);
		mRenderer.setPanEnabled(true, false);
		
		return ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);
	}
}

