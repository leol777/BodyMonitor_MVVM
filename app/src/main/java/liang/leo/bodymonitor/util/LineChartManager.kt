package liang.leo.bodymonitor.util

import android.graphics.Color
import android.graphics.Typeface
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.util.ArrayList
import java.util.List;


class LineChartManager(val lineChart: LineChart) {
    val leftAxis:YAxis = lineChart.axisLeft
    val rightAxis: YAxis? = lineChart.axisRight
    val xAxis = lineChart.xAxis

    private fun initLineChart(legendShow:Boolean){
        lineChart.setDrawGridBackground(false) //网格背景
        lineChart.setDrawBorders(false) //边界
        lineChart.animateX(1000) //动画效果
        lineChart.setTouchEnabled(true) //触摸事件
        lineChart.isDragEnabled = true //拖动事件
        lineChart.setScaleEnabled(false) //缩放事件
        lineChart.isScaleXEnabled = false //x轴缩放
        lineChart.isScaleYEnabled = false //y轴缩放
        lineChart.setPinchZoom(false) //双指缩放
        lineChart.isDoubleTapToZoomEnabled = false //双击缩放
        lineChart.isDragDecelerationEnabled = true //抬起手指继续滑动
        lineChart.setExtraOffsets(5F,25F,10F,0F) //设置边距

        val legend = lineChart.legend
        if(legendShow){
            legend.setDrawInside(false) //绘制于图形内部
            legend.textSize = 38f //文字大小
            legend.textColor = Color.BLUE //文字颜色
            legend.typeface = Typeface.DEFAULT_BOLD //字体风格
            legend.formSize = 50f //图例大小
            legend.formToTextSpace = 20f //文字图像距离
            legend.form = Legend.LegendForm.LINE //设置图例类型为线条
            legend.yOffset = 20f //底部间距

            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM //向下对齐
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER //中间对齐
            legend.orientation = Legend.LegendOrientation.HORIZONTAL //横向排列
        }else{
            legend.form = Legend.LegendForm.NONE
        }
    }

    public fun setYAxis(max:Float, min:Float, labelCount:Int){
        if (max < min) {
            return;
        }
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawAxisLine(false);        // 显示数字但不显示线
        leftAxis.setTextSize(10f);//设置文本大小
        leftAxis.setTextColor(Color.parseColor("#609ee9"));
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setAxisLineWidth(1f);
        leftAxis.setAxisLineColor(Color.parseColor("#609ee9"));
        leftAxis.setXOffset(5f);//设置15dp的距离
        leftAxis.setDrawGridLines(true);//是否绘制网格线(横线）
//        设置网格为虚线
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setGridColor(Color.parseColor("#D4DFF5"));
        // 线跟数据都不显示
        leftAxis.setAxisMaximum(max);//设置最大值
        leftAxis.setAxisMinimum(min);//设置最小值
        leftAxis.setLabelCount(labelCount, false);//显示7个
        rightAxis?.setEnabled(false); //右侧Y轴不显示
        rightAxis?.setAxisMinimum(0f);
        rightAxis?.setAxisMaximum(max);
        rightAxis?.setAxisMinimum(min);
        rightAxis?.setLabelCount(labelCount, false);
        lineChart.invalidate();
    }

    public fun setXAxis(max:Float, min:Float, labelCount:Int) {
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //X轴设置显示位置在底部
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);//间隔1
        xAxis.setDrawGridLines(false);//不绘制网格线,竖向的
        xAxis.setGridColor(Color.parseColor("#d8d8d8"));
        xAxis.setAvoidFirstLastClipping(true);        //设置最后和第一个标签不超出x轴
        xAxis.setAxisLineWidth(1.0f);//        设置线的宽度
        xAxis.setAxisLineColor(Color.parseColor("#609ee9"));
        xAxis.setAxisMaximum(max);//设置最大值
        xAxis.setAxisMinimum(min);//设置最小值,小技巧，通过设置Axis的最小值设置为负值可以改变距离与Y轴的距离
        xAxis.setLabelCount(labelCount, true);
        xAxis.setTextColor(Color.parseColor("#609ee9"));//设置字体颜色
        xAxis.setTextSize(10f);//设置字体大小
        xAxis.setLabelRotationAngle(0f);        //文字倾斜度
//        xAxis.setLabelCount(xAxisValues.size(), false);        //设置X轴的刻度数
        lineChart.invalidate();
    }

    public fun setDescription(use:Boolean, str:String, textSize:Float, textColor:Int,
        position_x:Float = 0f, position_y:Float = 0f){
        val description = Description();
        description.setText(str);//设置文本
        description.setTextSize(textSize); //设置文本大小，最小6f，最大16f。
        description.setTextColor(textColor);//设置文本颜色
        description.setPosition(position_x,position_y);//设置文本的位置
        lineChart.setDescription(description);//添加给LineChart
        description.setEnabled(use);/*不启用*/
        lineChart.invalidate();
    }

    public fun showLineChart(xAxisValue:FloatArray, yAxisValue:FloatArray, label:String,
        color:Int, fillColor:Int){
        initLineChart(false)
        val entries = ArrayList<Entry>()
        for (i in 0..xAxisValue.size){
            entries.add(Entry(xAxisValue.get(i),yAxisValue.get(i)))
        }

        val ds:LineDataSet = LineDataSet(entries, label)
        initLineDataSet(ds, color.toInt(), true, fillColor)
        val data = LineData(ds)
        lineChart.data = data
    }

    private fun initLineDataSet(lineDataSet:LineDataSet, color: Int, mode:Boolean, fillcolor: Int) {
        lineDataSet.setCubicIntensity(0.2f);//设置曲线的Mode强度，0-1
        lineDataSet.setColor(color.toInt());//设置折线的颜色,有三个构造方法Color.parseColor("#f7b851")
        lineDataSet.setCircleColor(color.toInt());//一次性设置所有圆点的颜色
        //       lineDataSet.setCircleColors(Color.RED,Color.BLACK,Color.GREEN);//依次设置每个圆点的颜色
        lineDataSet.setLineWidth(2f);//设置折线的宽度
        lineDataSet.setCircleRadius(3f);//设置圆点半径大小
        lineDataSet.setDrawCircleHole(true);   //设置曲线值的圆点是否是空心
        lineDataSet.setValueTextSize(9f);
        lineDataSet.setHighLightColor(color.toInt()); //高亮的线的颜色
//        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setDrawFilled(mode);//是否绘制折线下方的填充 true,默认false
        lineDataSet.setFillColor(fillcolor.toInt()); //设置填充颜色
        lineDataSet.setFormLineWidth(2f);//只有lineDataSet.setForm(Legend.LegendForm.LINE);时才有作用 这里我们设置的是圆所以这句代码直接注释
//       lineDataSet.setFormSize(15.f);
        lineDataSet.setMode(LineDataSet.Mode.LINEAR); //设置折线类型,线模式为圆滑曲线（默认折线），CUBIC_BEZIER为贝塞尔曲线
        lineDataSet.setDrawValues(true); // 不显示具体值
        lineDataSet.setDrawCircles(true);//是否绘制圆点，若为false则表示只有折线
    }

    public fun showLineChart(xAxisValues:List<Float>, yAxisValues:List<List<Float>> , labels:List<String>, colours:List<Int>, fillcolors:List<Int>) {
        initLineChart(true);
        val dataSets = ArrayList<ILineDataSet>();
        for (i in 0 until yAxisValues.size) {
            val entries = ArrayList<Entry>();
            for (j:Int in 0 until yAxisValues.get(i).size) {
                var j = j
                if (j >= xAxisValues.size) {
                    j = xAxisValues.size - 1;
                }
                entries.add(Entry(xAxisValues.get(j), yAxisValues.get(i).get(j)));
            }
            val lineDataSet = LineDataSet(entries, labels.get(i));
            initLineDataSet(lineDataSet, colours.get(i), true, fillcolors.get(i));
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            dataSets.add(lineDataSet);
        }
        val data = LineData(dataSets);
        xAxis.setLabelCount(xAxisValues.size, true);
        lineChart.setData(data);
    }



}


