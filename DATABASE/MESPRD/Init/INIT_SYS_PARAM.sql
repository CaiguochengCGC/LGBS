/*
�������:
01 ϵͳ����
*/
USE WebReport_DB
DELETE FROM SYS_PARAM;

INSERT INTO SYS_PARAM(PARAM_GROUP,PARAM_CODE,PARAM_NAME,PARAM_VAL, UDA_1, UDA_2,NOTE,IS_EDIT,PARAM_TYPE)
          SELECT '01',  'DEFAULT_PWD',              	'�û���ʼ����',                  '123456',  		'^[0-9a-zA-Z_]+$',        'ֻ���������֡�Ӣ����ĸ���»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PAGE_SIZE',                	'���ÿҳ����',                  '100',     		'^[0]*[1-9][0-9]{0,3}$',  'ֻ������1~4λ������',           '',     '2', 'NUMERIC'  
UNION ALL SELECT '01',  'MAX_PAGE_SIZE',            	'���ÿҳ�������',              '500',     		'^[0]*[1-9][0-9]{0,3}$',  'ֻ������1~4λ������',           '',     '2', 'NUMERIC'   
UNION ALL SELECT '01',  'MSGBOX_SHOW_TIME',         	'�ɹ���ʾ����ʾʱ�������룩',   	'1000',    		'^[0]*[1-9][0-9]{3}$',    'ֻ������4λ������',            '1��',  '2', 'NUMERIC'
UNION ALL SELECT '01',  'EXPORT_PAGE_SIZE',         	'�����������',       			'99999',    	'^[0]*[1-9][0-9]{0,3}$',   'ֻ������1~4λ������',          '',  '2', 'CHAR' 
UNION ALL SELECT '01',  'PMC_URL',						'PMC��ػ����ַ',            	'http://10.92.23.164/ProficyWebSpace/main.html',  	'^[0-9a-zA-Z_./:]+$',        'ֻ���������֡�Ӣ����ĸ���»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'WORK_DATE_START',         	'��ʼʱ��',       				'08:00:00',    	'^[0-9:]{8}$',   'ֻ���������ֺ�ð�ŵ�����',          'ʱ���ʽ(24Сʱ��)��hh:mi:ss (08:00:00)',  '2', 'CHAR' 
UNION ALL SELECT '01',  'WORK_DATE_IS',         	'�Ƿ����',       				'1',    		'^[0-1]{0,1}$',  'ֻ������1λ������',          	'1��ʾ���죬0��ʾ�����졣',  '2', 'CHAR' 
UNION ALL SELECT '01',  'WORK_DATE_END',         	'����ʱ��',       				'08:00:00',    	'^[0-9:]{8}$',   'ֻ���������ֺ�ð�ŵ�����',          'ʱ���ʽ(24Сʱ��)��hh:mi:ss (08:00:00)',  '2', 'CHAR' 

UNION ALL SELECT '01',  'PRODUCT_STATION*01_FL',    	'FL���',             			'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*02_RL',    	'RL����',             			'BRL100',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*03_BLD',		'BLD��ƴ',            			'BLD100',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*04_BPFI',		'FI����ƴ',            			'FI110',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*05_IPUB',		'UF�װ���ƴ',            		'UF080',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*06_BPUB',		'UB�װ���ƴ',            		'UB100',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*07_ASFE',		'AS21ǰ��',            			'FE050',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*08_ASRF',		'AS21��Χ',            			'RF040',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*09_ZPFERF',	'ZPǰ��',            			'FE070',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*10_ZPFERF',	'ZP��Χ',            			'RF070',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*11_ZPMF',		'ZP�еװ�',            			'MF050',  			'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*12_BPFERF',	'BP31ǰ��',            			'FE060',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*13_BPFERF',	'BP31��Χ',            			'RF010',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*14_BPBSL',		'AS21/BP31���Χ',            		'ASRH050,BPLH050',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*15_BPBSR',		'AS21/BP31�Ҳ�Χ',            		'ASLH050,BPRH050',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*16_ZPBS',		'ZP��Χ',            			'BSORH070',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*17_IPBS',		'IP��Χ',            			'BSOLH060A',  			'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*18_ZPFDL',		'ZP11���ŷ�ƴ',            			'PRESS080',  	'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*19_ZPFDR',		'ZP11���ŷ�ƴ',            			'PRESS080',  	'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*20_ZPTG',		'ZP11ǰ��/β�ŷ�ƴ',            	'PRESS050',  	'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*21_IPFD',		'IP���ŷ�ƴ',            		'FDL020T1',  	'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*22_IPFD',		'IP���ŷ�ƴ',            		'FDL020T1',  	'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*23_ASHDL',		'BP31/AS21ǰ��β��',            	'PRESS060',  	'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*24_ASFDL',		'BP31/AS21����',            		'PRESS040',  	'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*25_ASFDR',		'BP31/AS21����',            		'PRESS040',  	'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*26_SR',		'FL���˷�����',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*27_RBB',		'RL��������',            		'FL240',  		'^[0-9a-zA-Z_]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*28_MBB',		'BLD��ƴ����',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*29_IPUBB',		'UF�װ建��',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*30_ZPUBB',		'ZP�װ���ƴ����',            	'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*31_BPUBB',		'BP31�װ���ƴ����',            	'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*32_ASFERF',	'AS21ǰ�պ�Χ����',            	'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*33_PICK',		'����PICKUP��',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*34_BPFDS',		'BP31�װ��ƴ����',            	'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*35_ZPFDS',		'ZP�װ��ƴ����',            	'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*36_BPEMS',		'BP31��Χ����',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*37_EMS',		'IP/ZP��Χ����',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
UNION ALL SELECT '01',  'PRODUCT_STATION*38_ASFI',		'����ƴ����',            		'FL240',  		'^[0-9a-zA-Z_,]+$',        'ֻ���������֡�Ӣ����ĸ�����ź��»���', '',    '2',  'CHAR' 
 --
;


