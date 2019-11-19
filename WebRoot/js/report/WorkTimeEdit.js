// /**
//  * 休息时间页面
//  */
// (function() {
//     var setPanelIsLayout;
//
//     //休息页面录入界面
//     var stopLinePanel = new ht.base.RowEditorPanel({
//         region : 'center',
//         queryFormConfig : {
//             enableQueryButton : true,
//             items : [{
//                 xtype: 'compositefield',
//                 fieldLabel: '录入时间',
//                 name : 'PLAN_DATE',
//                 items: [ {
//                     xtype : 'datefield',
//                     format : ht.config.format.DATE,
//                     fieldLabel : ' ',
//                     name : 'START_PLAN_DATE',
//                     value : new Date(new Date().getTime() - (new Date().format('N') + 1) * 24 * 60 * 60 * 1000),
//
//                     vtype: 'defineValid',
//                     vtypeText : '',
//                     defineValid : function(val, fromField){
//                         if(setPanelIsLayout && stopLinePanel.findQueryCompment('END_PLAN_DATE')){
//                             stopLinePanel.findQueryCompment('END_PLAN_DATE').validate();
//                         }
//                         return true;
//                     }
//                 }, {
//                     xtype: 'displayfield',
//                     width : 20,
//                     style : 'text-align: center;',
//                     value : '至'
//                 },{
//                     xtype : 'datefield',
//                     format : ht.config.format.DATE,
//                     fieldLabel : ' ',
//                     name : 'END_PLAN_DATE',
//                     value : new Date(new Date().getTime()),
//                     vtype: 'comparentDate',
//                     vtypeText: ht.msg.valid.compareDate,
//                     comparentTo: 'START_PLAN_DATE',
//                     getParentCompont : function() {
//                         return stopLinePanel.ht_outputQueryForm.find('name', 'PLAN_DATE')[0].innerCt;
//                     }
//                 }]
//             }],
//             action : 'QUERY_Work_Time_ACTION',
//             outputTable : 'PMC_VIEW_RECORD'
//         },
//         gridConfig : {
//             columns : [new Ext.grid.RowNumberer(),{
//                 header : '录入时间',
//                 dataIndex : 'RECORDDATE',
//                 width : 150,
//                 editor : {
//                     xtype : 'datetimefield',
//                     name : 'RECORDDATE',
//                     format : ht.config.format.DATETIME
//                 },
//                 renderer : function(value, p,record) {
//                     return ht.pub.date.dateTimeRenderer(value);//显示时间
//                 }
//
//             },{
//                 header : '车间名字',
//                 dataIndex : 'WORKSHOP',
//                 width : 150,
//                 editor : {
//                     xtype : 'textfield',
//                     name : 'WORKSHOP'
//                 }
//             },{
//                 header : '开始工作时间',
//                 dataIndex : 'BEGIN_TIME',
//                 width : 150,
//                 editor : {
//                     xtype : 'datetimefield',
//                     name : 'BEGIN_TIME',
//                     format : ht.config.format.DATETIME
//                 },
//                 renderer : function(value, p,record) {
//                     return ht.pub.date.dateTimeRenderer(value);//显示时间
//                 }
//             },{
//                 header : '结束工作时间',
//                 dataIndex : 'END_TIME',
//                 width : 150,
//                 editor : {
//                     xtype : 'datetimefield',
//                     name : 'END_TIME',
//                     format : ht.config.format.DATETIME
//                 },
//                 renderer : function(value, p,record) {
//                     return ht.pub.date.dateTimeRenderer(value);//显示时间
//                 }
//             },{
//                 header : '休息时间',
//                 dataIndex : 'REST_TIME',
//                 width : 150,
//                 editor : {
//                     xtype : 'textfield',
//                     name : 'REST_TIME'
//                 }
//             },{
//                 header : '休息次数',
//                 dataIndex : 'REST_COUNT',
//                 width : 150,
//                 editor : {
//                     xtype : 'textfield',
//                     name : 'REST_COUNT'
//                 }
//             },{
//                 header : '工作总时间',
//                 dataIndex : 'TOTAL_TIME',
//                 width : 150,
//                 editor : {
//                     xtype : 'textfield',
//                     name : 'TOTAL_TIME'
//                 }
//             },{
//                 header : '工作日',
//                 dataIndex : 'WORK_DATE',
//                 width : 150,
//                 editor : {
//                     xtype : 'textfield',
//                     name : 'WORK_DATE'
//                 }
//             },{
//                 header : '班次',
//                 dataIndex : 'SHIFT',
//                 width : 150,
//                 editor : {
//                     xtype : 'textfield',
//                     name : 'SHIFT'
//                 }
//             } ],
//
//             isPageAction : false,
//             isMultipleSelect : false,
//             storeFields : [
//             	'ID',
//             	'WORKSHOP',
//             	'BEGIN_TIME',
//             	'END_TIME',
//             	'REST_TIME',
//             	'REST_COUNT',
//             	'TOTAL_TIME',
//             	'WORK_DATE',
//             	'SHIFT',
//             	'IS_WORK_DATE',{name : 'RECORDDATE',REF_TYPE : ht.pub.date.DATE_TIME}
//
//             	],
//             deletedKeyFields : ['ID'],
//
//             //增加按钮
// 	        addBtn : {
// 	            action : 'ADD_PMC_VIEW_RECORD_ACTION',
// 	            outputTable : '', //数据返回的output
// 	            tagValue : '',
// 	            position : 1
// 	        },
//
// 	        //修改按钮
// 	        updateBtn : {
// 	            action : 'UPDATE_PMC_VIEW_RECORD_ACTION',
// 	            outputTable : '', //数据返回的output
// 	            tagValue : '',
// 	            position : 2
// 	        },
//
// 	        //删除按钮
// 	        deleteBtn : {
// 	            action : 'DELETE_PMC_VIEW_RECORD_ACTION',
// 	            tagValue : '',
// 	            position : 3
// 	        }
//
//         },
//         ctListeners : {
//
//         },
//         listeners : {
//             afterLayout : function(){
//                 setPanelIsLayout = true;
//             }
//         }
//     });
//
//
//
//     var backPanel =new Ext.Panel({
//         border : false,
//         layout : 'border',
//         items : [stopLinePanel],
//         listeners : {
//             beforedestroy : function(){
//                 backPanel.removeAll(true);
//             }
//         }
//     });
//
//     return backPanel;
// })();
