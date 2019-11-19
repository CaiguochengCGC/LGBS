
Ext.namespace('ht.pub.global');

//一天
ht.pub.global.ONE_DAY = 24 * 60 * 60 * 1000;

//是否
ht.pub.global.YESNO = { 
    YES : 1, 
    NO : 0
};

//操作指令
ht.pub.global.CMD = {
    ADD : 'A',
    UPDATE : 'U',
    DELETE : 'D'
}

//记录类型
ht.pub.global.RECORD_TYPE = {
    AUTO : '0',
    MANUAL : '1'
}

//记录类型
ht.pub.global.QUERY_TYPE = {
    DAY : '0',
    WEEK : '3',
    MONTH : '1',
    YEAR : '2'
}

ht.pub.global.SCALE = 2;
ht.pub.global.STEAM_SCALE = 5;