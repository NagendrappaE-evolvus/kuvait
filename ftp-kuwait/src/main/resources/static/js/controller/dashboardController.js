app.controller("DashboardController", [
		"$scope",
		"$http",
		"$rootScope",
		"$state",
		"MasterService",
		"AuthService",
		"$transitions",
		function($scope, $http, $rootScope, $state, MasterService, AuthService,
				$transitions) {
			Highcharts.chart('line-chart', {
				title : {
					text : 'Sum of Foreign Currency Balance against Scheme Codes on 31 August, 2017 and Total till same date.'
				},

				xAxis : {
					categories : [ '360', 'CAAA', 'CACOR', 'CARET', 'CASH',
							'CASTF', 'DPNL', 'LCBD', 'LF', 'LFR', 'LIABL',
							'MOR', 'NCL', 'ODCBD', 'PDCP', 'REA', 'SBGEC',
							'SBGER', 'SCALC', 'SCALR', 'TDCTD', 'TDRTD'

					]
				},

				series : [
						{
							name : "31/08/2017",
							data : [ 667877656.5, 5126578.99, 282016849.4,
									98972003.1, -7290618.29, 4106968.04,
									386750813.3, 10250636.42, -1140630793,
									-77199999.7, 130724190.7, -13515767.04,
									-194861.47, -430238188.2, -103559,
									-262545446.3, 761522.36, 25776705.98,
									56595614.11, 7673153.79, 1577809331,
									96749917.43, 1419472708 ]

						},
						{
							name : "Total",
							data : [ 667877656.5, 5126578.99, 282016849.4,
									98972003.1, -7290618.29, 4106968.04,
									386750813.3, 10250636.42, -1140630793,
									-77199999.7, 130724190.7, -13515767.04,
									-194861.47, -430238188.2, -103559,
									-262545446.3, 761522.36, 25776705.98,
									56595614.11, 7673153.79, 1577809331,
									96749917.43 ]
						} ]
			});
			
			Highcharts.chart('dot-chart', {
			    chart: {
			        plotBackgroundColor: null,
			        plotBorderWidth: null,
			        plotShadow: false,
			        type: 'column'
			    },
			    title: {
			        text: 'Sum of Foreign Currency Balance against Scheme Codes on 31 August, 2017'
			    },
			    tooltip: {
			        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			    },
			    plotOptions: {
			        pie: {
			            allowPointSelect: true,
			            cursor: 'pointer',
			            dataLabels: {
			                enabled: true,
			                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
			                style: {
			                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
			                }
			            }
			        }
			    },
			    series: [{
			         name:'Schemes',
			        colorByPoint: true,
			        data: [
			        	{name:'360',y:667877656.5},
			        	{name:'CAAA',y:	5126578.99},
			        	{name:'CACOR',y:	282016849.4},
			        	{name:'CARET',y:	98972003.1},
			        	{name:'CASH',y:	-7290618.29},
			        	{name:'CASTF',y:	4106968.04},
			        	{name:'DPNL',y:	386750813.3},
			        	{name:'LCBD',y:	10250636.42},
			        	{name:'LF',y:	-1140630793},
			        	{name:'LFR',y:	-77199999.7},
			        	{name:'LIABL',y:	130724190.7},
			        	{name:'MOR',y:	-13515767.04},
			        	{name:'NCL',y:	-194861.47},
			        	{name:'ODCBD',y:	-430238188.2},
			        	{name:'PDCP',y:	-103559},
			        	{name:'REA',y:	-262545446.3},
			        	{name:'SBGEC',y:	761522.36},
			        	{name:'SBGER',y:	25776705.98},
			        	{name:'SCALC',y:	56595614.11},
			        	{name:'SCALR',y:	7673153.79},
			        	{name:'TDCTD',y:	1577809331},
			        	{name:'TDRTD',y:	96749917.43}
]
			    }]
			});
		} ]);
