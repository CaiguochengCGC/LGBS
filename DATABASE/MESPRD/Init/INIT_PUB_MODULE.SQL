DELETE FROM PUB_MODULE;

INSERT INTO PUB_MODULE(PARENT_MODULE, PK_MODULE_CODE, MODULE_NAME,SORT,MODULE_LEVEL)
          SELECT 'ALL',      'COMPANY',   '��������',          100, 1  
UNION ALL SELECT 'ALL',      'CAF',       '��ѹ���ݷ���',      200, 1
UNION ALL SELECT 'ALL',      'BODY',          '�������ݷ���',      300, 1
UNION ALL SELECT 'BODY',     'BODY_REPORT',   '���䱨��',      310, 1
UNION ALL SELECT 'BODY',     'BODY_ANALYSE',          '���ݷ������ѯ',      320, 1
UNION ALL SELECT 'BODY_ANALYSE',     'BODY_ANALYSE_EQUIP',          'ƿ���豸',      321, 1
UNION ALL SELECT 'BODY_ANALYSE',     'BODY_ANALYSE_STATION',          'ƿ����λ',      322, 1
UNION ALL SELECT 'ALL',      'PAINT',          '�������ݷ���',      400, 1
UNION ALL SELECT 'ALL',      'ASSEMB',          '��װ���ݷ���',      500, 1
UNION ALL SELECT 'ALL',      'BDC',       'BDC���ݷ���',       600, 1
UNION ALL SELECT 'ALL',      'MONITOR',       'PMCʵʱ��ػ���',   700, 1
UNION ALL SELECT 'ALL',      'PUB',       '����ģ��',          800, 1
;