package com.stockholdergame.client.ui.components.chart.chartClasses {

    [Bindable]
    public class ChartPointData {
        public function ChartPointData(stackName:String, values:Array) {
            this.stackName = stackName;
            this.values = values;
        }

        public var stackName:String;
        public var values:Array;
    }
}
