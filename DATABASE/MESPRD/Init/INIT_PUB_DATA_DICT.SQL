DELETE FROM PUB_DATA_DICT;

--
INSERT INTO PUB_DATA_DICT(PK_ID,CODE_TYPE, CODE_TYPE_NAME, CODE_VALUE, CODE_VALUE_NAME, SORT_NO,IS_EDIT,OTHER_CODE_VALUE1) 
          SELECT NEWID(), 'EDIT_STATUS', '编辑状态',       '0',   '不可见',           '1', '0', ''  
UNION ALL SELECT NEWID(), 'EDIT_STATUS', '编辑状态',       '1',   '可见不可编辑',     '2', '0', ''  
UNION ALL SELECT NEWID(), 'EDIT_STATUS', '编辑状态',       '2',   '可编辑',           '3', '0', ''  
--
--
UNION ALL SELECT NEWID(), 'PRIVI_TYPE',   '权限类型',       '0',   '菜单',            '1', '1', ''  
UNION ALL SELECT NEWID(), 'PRIVI_TYPE',   '权限类型',       '1',   '数据过滤',        '2', '1', ''  
UNION ALL SELECT NEWID(), 'PRIVI_TYPE',   '权限类型',       '2',   '功能',            '3', '1', ''  
--
--
UNION ALL SELECT NEWID(), 'YES_NO',  '是否',        '1',   '是',        '1', '1', ''  
UNION ALL SELECT NEWID(), 'YES_NO',  '是否',        '0',   '否',        '2', '1', ''  
--
UNION ALL SELECT NEWID(), 'TOLTLAG',  '报表级别',        '1',   '车间',        '1', '1', ''  
UNION ALL SELECT NEWID(), 'TOLTLAG',  '报表级别',        '2',   '工段',        '2', '1', ''

--
UNION ALL SELECT NEWID(), 'FACTORY_TYPE',     '工厂',  '南京',    '南京',           '10', '1', ''  
--
UNION ALL SELECT NEWID(), 'WORKSHOP_TYPE',    '车间',  '车身',    '车身',           '10', '1', ''
--
UNION ALL SELECT NEWID(), 'SHIFT_TYPE',       '班别',  '1班',    '1班',           '10', '1', ''  
UNION ALL SELECT NEWID(), 'SHIFT_TYPE',       '班别',  '2班',    '2班',           '20', '1', ''  
UNION ALL SELECT NEWID(), 'SHIFT_TYPE',       '班别',  '3班',    '3班',           '30', '1', '' 
--
UNION ALL SELECT NEWID(), 'PRODUCTIONLINE',       '工段',    'FE',    'FE',           '10', '1', ''  
UNION ALL SELECT NEWID(), 'PRODUCTIONLINE',       '工段',    'UB',    'UB',           '20', '1', ''  
UNION ALL SELECT NEWID(), 'PRODUCTIONLINE',       '工段',    'FL',    'FL',           '30', '1', '' 


--
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '01',   '总装',        '1', '2', ''
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '02',   '油漆',        '2', '2', ''
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '03',   '冲压',        '3', '2', ''
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '04',   '物料',        '4', '2', ''
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '05',   'BSSQE',       '5', '2', ''
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '06',   '陆威',        '6', '2', ''
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '07',   'ME',       	'7', '2', ''
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '08',   'SQE',        	'8', '2', ''
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '09',   'PE',        	'9', '2', ''
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '10',   'IT',        	'10', '2', ''
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '11',   '项目车',       '11', '2', ''
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '12',   '堵线',         '12', '2', ''
UNION ALL SELECT NEWID(), 'ARESPON',  '责任部门',        '13',   'TBD/其它',     '13', '2', ''
;

