package com.example.bpmanager;

//import org.achartengine.chart.PieChart;
//import org.achartengine.chart.LineChart;
//import org.achartengine.model.CategorySeries;
//import org.achartengine.renderer.DefaultRenderer;
//import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.view.View;

public class bpGraph extends View {

	public bpGraph(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

//	private PieChart mPieChart = null;
//	private LineChart mLineChart = null;
//    private MainActivity parent;
//    private FrameLayout target;
//    public bpGraph(FrameLayout f) {
//        super(f.getContext());
//        setFocusable(true);
//        target = f;
//        
//    }
//    
//    public bpGraph(FrameLayout f, AttributeSet attrs){
//        
//        super(f.getContext(),attrs);
//        setFocusable(true);
//        target = f;
//    }
//    
//    public bpGraph(FrameLayout f, AttributeSet attrs, int defaultStyle)
//    {
//        super(f.getContext(), attrs, defaultStyle);
//        setFocusable(true);
//        target = f;
//    }
//    
//    public void makeChart(){
//        
//        double[] values = new double[] {10,20,30,40}; //�� �迭(Series)�� ��
//        int[] colors = new int[]{Color.CYAN, Color.MAGENTA,  Color.YELLOW, Color.GREEN}; //�� �迭(Series) ����
//        String[] texts = new String[] {"SAMPLE1", "SAMPEL2", "SAMPLE3", "SAMPLE4" }; //�� �迭(Series) ��
//        
//        //��Ʈ���� ���� ������(�������� ��, ����, �ؽ�Ʈ)�� ��Ʈ�� ���� �� �ʿ��� 
//        //DefaultRenderer��ü�� CategorySeries��ü�� �����Ѵ�.
//        
//        DefaultRenderer renderer = new DefaultRenderer();
//        for(int color:colors){
//            SimpleSeriesRenderer ssr = new SimpleSeriesRenderer();
//            ssr.setColor(color);
//            renderer.addSeriesRenderer(ssr);
//        }
//        
//        //CategorySeries(java.lang.String title) 
//        CategorySeries series = new CategorySeries("�迭 Ÿ��Ʋ");
//        int count = 0;
//        for(double value: values){
//            //add(java.lang.String category, double value) 
//            series.add(texts[count++],value);
//        }
//        //PieChart ������ -> PieChart(CategorySeries dataset, DefaultRenderer renderer)
//        mPieChart = new PieChart(series, renderer);
//        
//        //�信 �ٽ� �׸���
//        invalidate();
//    }
//    
//    @Override
//    protected void onDraw(Canvas canvas)
//    {
//        int width = getMeasuredWidth(); 
//        int height = getMeasuredHeight();
//        Log.i("ChartView","onDraw->Width:"+width+"/height:"+height);
//        
//        if (mPieChart != null) //mPieChart�� ���� �ƴϸ� ��Ʈ�� ���������� �׸�
//            mPieChart.draw(canvas, 0, 0, width-10, height-10, null);
//    }
}