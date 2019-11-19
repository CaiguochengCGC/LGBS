DELETE FROM PUB_MODULE;

INSERT INTO PUB_MODULE(PARENT_MODULE, PK_MODULE_CODE, MODULE_NAME,SORT,MODULE_LEVEL)
          SELECT 'ALL',      'COMPANY',   '工厂概览',          100, 1  
UNION ALL SELECT 'ALL',      'CAF',       '冲压数据分析',      200, 1
UNION ALL SELECT 'ALL',      'BODY',          '车身数据分析',      300, 1
UNION ALL SELECT 'BODY',     'BODY_REPORT',   '车间报表',      310, 1
UNION ALL SELECT 'BODY',     'BODY_ANALYSE',          '数据分析与查询',      320, 1
UNION ALL SELECT 'BODY_ANALYSE',     'BODY_ANALYSE_EQUIP',          '瓶颈设备',      321, 1
UNION ALL SELECT 'BODY_ANALYSE',     'BODY_ANALYSE_STATION',          '瓶颈工位',      322, 1
UNION ALL SELECT 'ALL',      'PAINT',          '油漆数据分析',      400, 1
UNION ALL SELECT 'ALL',      'ASSEMB',          '总装数据分析',      500, 1
UNION ALL SELECT 'ALL',      'BDC',       'BDC数据分析',       600, 1
UNION ALL SELECT 'ALL',      'MONITOR',       'PMC实时监控画面',   700, 1
UNION ALL SELECT 'ALL',      'PUB',       '公共模块',          800, 1
;
