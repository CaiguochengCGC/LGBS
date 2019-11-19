/*
参数类别:
01 系统参数
*/
USE WebReport_DB
DELETE FROM SYS_PARAM;

INSERT INTO SYS_PARAM(PARAM_GROUP,PARAM_CODE,PARAM_NAME,PARAM_VAL, UDA_1, UDA_2,NOTE,IS_EDIT,PARAM_TYPE)
          SELECT '01',  'DEFAULT_PWD',              	'用户初始密码',                  '123456',  		'^[0-9a-zA-Z_]+$',        '只能输入数字、英文字母和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PAGE_SIZE',                	'表格每页条数',                  '100',     		'^[0]*[1-9][0-9]{0,3}$',  '只能输入1~4位的整数',           '',     '2', 'NUMERIC'  
UNION ALL SELECT '01',  'MAX_PAGE_SIZE',            	'表格每页最大条数',              '500',     		'^[0]*[1-9][0-9]{0,3}$',  '只能输入1~4位的整数',           '',     '2', 'NUMERIC'   
UNION ALL SELECT '01',  'MSGBOX_SHOW_TIME',         	'成功提示框显示时长（毫秒）',   	'1000',    		'^[0]*[1-9][0-9]{3}$',    '只能输入4位的整数',            '1秒',  '2', 'NUMERIC'
UNION ALL SELECT '01',  'EXPORT_PAGE_SIZE',         	'导出最大条数',       			'99999',    	'^[0]*[1-9][0-9]{0,3}$',   '只能输入1~4位的整数',          '',  '2', 'CHAR' 
UNION ALL SELECT '01',  'PMC_URL',						'PMC监控画面地址',            	'http://10.92.23.164/ProficyWebSpace/main.html',  	'^[0-9a-zA-Z_./:]+$',        '只能输入数字、英文字母和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'WORK_DATE_START',         	'开始时间',       				'08:00:00',    	'^[0-9:]{8}$',   '只能输入数字和冒号的整数',          '时间格式(24小时制)：hh:mi:ss (08:00:00)',  '2', 'CHAR' 
UNION ALL SELECT '01',  'WORK_DATE_IS',         	'是否跨天',       				'1',    		'^[0-1]{0,1}$',  '只能输入1位的整数',          	'1表示跨天，0表示不跨天。',  '2', 'CHAR' 
UNION ALL SELECT '01',  'WORK_DATE_END',         	'结束时间',       				'08:00:00',    	'^[0-9:]{8}$',   '只能输入数字和冒号的整数',          '时间格式(24小时制)：hh:mi:ss (08:00:00)',  '2', 'CHAR' 

UNION ALL SELECT '01',  'PRODUCT_STATION*01_FL',    	'FL表调',             			'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*02_RL',    	'RL补焊',             			'BRL100',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*03_BLD',		'BLD总拼',            			'BLD100',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*04_BPFI',		'FI内总拼',            			'FI110',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*05_IPUB',		'UF底板总拼',            		'UF080',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*06_BPUB',		'UB底板总拼',            		'UB100',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*07_ASFE',		'AS21前舱',            			'FE050',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*08_ASRF',		'AS21后围',            			'RF040',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*09_ZPFERF',	'ZP前舱',            			'FE070',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*10_ZPFERF',	'ZP后围',            			'RF070',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*11_ZPMF',		'ZP中底板',            			'MF050',  			'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*12_BPFERF',	'BP31前舱',            			'FE060',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*13_BPFERF',	'BP31后围',            			'RF010',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*14_BPBSL',		'AS21/BP31左侧围',            		'ASRH050,BPLH050',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*15_BPBSR',		'AS21/BP31右侧围',            		'ASLH050,BPRH050',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*16_ZPBS',		'ZP侧围',            			'BSORH070',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*17_IPBS',		'IP侧围',            			'BSOLH060A',  			'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*18_ZPFDL',		'ZP11左门分拼',            			'PRESS080',  	'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*19_ZPFDR',		'ZP11右门分拼',            			'PRESS080',  	'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*20_ZPTG',		'ZP11前盖/尾门分拼',            	'PRESS050',  	'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*21_IPFD',		'IP左门分拼',            		'FDL020T1',  	'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*22_IPFD',		'IP右门分拼',            		'FDL020T1',  	'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*23_ASHDL',		'BP31/AS21前盖尾门',            	'PRESS060',  	'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*24_ASFDL',		'BP31/AS21左门',            		'PRESS040',  	'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*25_ASFDR',		'BP31/AS21右门',            		'PRESS040',  	'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*26_SR',		'FL空撬返回线',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*27_RBB',		'RL补焊缓存',            		'FL240',  		'^[0-9a-zA-Z_]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*28_MBB',		'BLD总拼缓存',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*29_IPUBB',		'UF底板缓存',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*30_ZPUBB',		'ZP底板总拼缓存',            	'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*31_BPUBB',		'BP31底板总拼缓存',            	'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*32_ASFERF',	'AS21前舱后围缓存',            	'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*33_PICK',		'空中PICKUP线',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*34_BPFDS',		'BP31底板分拼缓存',            	'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*35_ZPFDS',		'ZP底板分拼缓存',            	'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*36_BPEMS',		'BP31侧围缓存',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*37_EMS',		'IP/ZP侧围缓存',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*38_ASFI',		'内总拼缓存',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        '只能输入数字、英文字母、逗号和下划线', '',    '2',  'CHAR' 
 --
;


