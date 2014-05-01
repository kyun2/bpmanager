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
import android.graphics.Paint.Align;
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
		renderer.setChartValuesTextAlign(Align.CENTER);
		renderer.setChartValuesTextSize(40f);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setChartTitle("복용성취율 현황");		
		mRenderer.setChartTitleTextSize(30f);
		mRenderer.setXTitle("기간");
		mRenderer.setYTitle("복용성취율");
		mRenderer.setXLabels(0);
		mRenderer.setYLabels(20);
		mRenderer.setLabelsColor(Color.BLACK);
		mRenderer.setLabelsTextSize(20f);
		mRenderer.setXAxisMin(0.0);
		mRenderer.setXAxisMax(10.0);
		mRenderer.setYAxisMin(0.0);
		mRenderer.setYAxisMax(120.0);
		mRenderer.setAxisTitleTextSize(20f);
		mRenderer.setShowAxes(true);
		mRenderer.setGridColor(Color.BLACK);
		mRenderer.setShowGrid(true);
		mRenderer.setShowGridY(true);
		mRenderer.setShowGridX(true);
		mRenderer.setBarSpacing(0.5);
		mRenderer.setShowLegend(false);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setMarginsColor(Color.WHITE);
		mRenderer.setBackgroundColor(Color.LTGRAY);
		mRenderer.setZoomEnabled(false, false);
		mRenderer.setPanEnabled(true, false);
				
		return ChartFactory.getBarChartView(context, dataset, mRenderer, Type.DEFAULT);
	}
}

