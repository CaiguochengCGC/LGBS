USE WebReport_DB
DELETE FROM PUB_PRIVILEGES;

--TYPE : 0 菜单， 1 数据过滤，2 功能
--
--菜单权限值：第1级模块代码 + 第2级模块代码 + 第3级模块代码 + 权限代码 ,
--    例如：01 + 00 + 00 + 01 -> 01000001
--
--数据权限值（隶属模块功能）：第1级模块代码 + 第2级模块代码 + 第3级模块代码 + 'D0' + 权限代码
--    例如：05 + 00 + 00 + D0 + 01 -> 050000D001
--
--功能权限值（隶属菜单功能）：菜单权限值  + 权限代码
--    例如：01000001 + 01 -> 0100000101
-- 
--Ext权限
INSERT INTO PUB_PRIVILEGES(PK_PRIVILEGES_ID, TYPE, CONTENT,DESCRIPTION,CATEGORY,SORT, I18N_RESOURCES)    
		  SELECT '01000001', '0','report/company',       '工厂概览表',         'COMPANY',  '1000', '' 
		  
UNION ALL SELECT '02000001', '0','report/PmcSystemReport',  '车身生产简报',       'BODY_REPORT',  '2000', ''
UNION ALL SELECT '02000002', '0','report/kpiIndex',         'KPI指标报表',                'BODY_REPORT',  '3000', ''
UNION ALL SELECT '02000003', '0','report/yieldReport',      '产量报表',                   'BODY_REPORT',  '4000', ''
UNION ALL SELECT '02000004', '0','report/beatReport',       '节拍报表',                   'BODY_REPORT',  '5000', ''
UNION ALL SELECT '02000005', '0','report/oee',                 '开动率报表',               'BODY_REPORT',  '6000', ''
UNION ALL SELECT '02000021', '0','report/oeeCount',                 '开动率汇总报表',               'BODY_REPORT',  '6001', ''
UNION ALL SELECT '02000006', '0','report/tabStopLine',      '工位停机报表',            'BODY_REPORT',  '7000', ''
UNION ALL SELECT '02000007', '0','report/tabEquStopLine',   '工段停线报表',                'BODY_REPORT',  '8000', ''

UNION ALL SELECT '03000001', '0','report/btneckEquQuryTime', '停机时间',    'BODY_ANALYSE_EQUIP',  '9000', ''
UNION ALL SELECT '03000002', '0','report/btneckEquQuryFq',   '停机频次',     'BODY_ANALYSE_EQUIP',  '10000', ''

UNION ALL SELECT '04000001', '0','report/tabStationStopTime', '停机时间',     'BODY_ANALYSE_STATION',  '1000', ''
UNION ALL SELECT '04000002', '0','report/tabStationStopCt',   '停机次数',     'BODY_ANALYSE_STATION',  '2000', ''
UNION ALL SELECT '04000003', '0','report/tabStationStopTakt', '工位节拍',     'BODY_ANALYSE_STATION',  '3000', ''

UNION ALL SELECT '05000004', '0','report/stopLineQuery',   	'停线查询',     'BODY_ANALYSE',  '1000', ''
UNION ALL SELECT '05000005', '0','report/presponQuery',   	'责任部门停线查询',     'BODY_ANALYSE',  '1010', ''
UNION ALL SELECT '05000006', '0','report/alarmEvent',         '停线与故障事件对应表',  'BODY_ANALYSE',  '1020', ''
UNION ALL SELECT '05000001', '0','report/faultEvent',         '故障事件记录',  'BODY_ANALYSE',  '1100', ''
UNION ALL SELECT '05000002', '0','report/outputQueryReport',   	'产量查询',     'BODY_ANALYSE',  '2000', ''
UNION ALL SELECT '05000003', '0','report/realWorkTimeReport',   	'实际生产时间查询',     'BODY_ANALYSE',  '3000', ''


UNION ALL SELECT '06000001', '0','pub/pub_DataDict',       '数据字典',         'PUB',  '1000', 'pub/pub'  
UNION ALL SELECT '06000002', '0','pub/pub_RolePrivileges', '角色权限维护',      'PUB',  '2000', 'pub/pub' 
UNION ALL SELECT '06000003', '0','pub/pub_UserRole',       '用户信息维护',      'PUB',  '3000', 'pub/pub' 
UNION ALL SELECT '06000004', '0','pub/pub_SysParam',       '参数维护',          'PUB',  '4000', 'pub/pub'
UNION ALL SELECT '06000005', '0','report/planDateImportReport',   '计划时间维护',     'PUB',  '5000', ''
UNION ALL SELECT '06000007', '0','report/lastStationMaintain',   '最后工位维护',     'PUB',  '5010', ''
UNION ALL SELECT '06000006', '0','report/feedBack',        '意见反馈',          'PUB',  '6000', ''

--UNION ALL SELECT '07000001', '0','report/',        '',          'CAF',  '1000', ''
--UNION ALL SELECT '08000001', '0','report/',        '',          'PAINT', '1000', ''
--UNION ALL SELECT '09000001', '0','report/',        '',          'ASSEMB',  '1000', ''
--UNION ALL SELECT '10000001', '0','report/',        '',          'BDC',  '1000', ''
UNION ALL SELECT '11000001', '0','pmc/pmc',        'PMC监控画面',          'MONITOR',  '1000', ''
; 

