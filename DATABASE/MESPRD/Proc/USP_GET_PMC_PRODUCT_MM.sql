USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_PRODUCT_MM]    Script Date: 08/24/2015 09:48:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

---按照每天的早上8：10分运行
-----产量月报表存储过程    
ALTER  PROCEDURE  [dbo].[USP_GET_PMC_PRODUCT_MM] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --错误标志：0.成功,1.错误
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --错误信息
As 
Begin
DECLARE  @PLANPRODUCTNUM INT;--计划数量
DECLARE  @PPRODUCTIONLINE  varchar(1024) ;--线体
DECLARE  @PPRODUCTIONLINENAME   varchar(1024) ;---线体名称 
--DECLARE  @PFRODUCTIONLINE   varchar(1024) ;---线体归类 
DECLARE  @PMONTHPLANCOUNT  int;--本月计划产量
DECLARE  @PMONTHREALCOUNT  int;--本月实际产量
DECLARE  @PMONTHRATE  numeric(6,2) ;--本月完成率
DECLARE	 @SEQ	int;--排序规则
DECLARE @VARCHARPMONTHRATE  varchar(1024) ;
DECLARE  @PDATA1  int;----
DECLARE  @PDATA2 int;
DECLARE  @PDATA3  int;----
DECLARE  @PDATA4 int;
DECLARE  @PDATA5 int;
DECLARE  @PDATA6  int;
DECLARE  @PDATA7 int;
DECLARE  @PDATA8  int;
DECLARE  @PDATA9 int;
DECLARE  @PDATA10 int;
DECLARE  @PDATA11  int;----
DECLARE  @PDATA12 int;
DECLARE  @PDATA13  int;----
DECLARE  @PDATA14 int;
DECLARE  @PDATA15 int;
DECLARE  @PDATA16  int;
DECLARE  @PDATA17 int;
DECLARE  @PDATA18  int;
DECLARE  @PDATA19 int;
DECLARE  @PDATA20 int;
DECLARE  @PDATA21  int;----
DECLARE  @PDATA22 int;
DECLARE  @PDATA23  int;----
DECLARE  @PDATA24 int;
DECLARE  @PDATA25 int;
DECLARE  @PDATA26  int;
DECLARE  @PDATA27 int;
DECLARE  @PDATA28  int;
DECLARE  @PDATA29 int;
DECLARE  @PDATA30 int;
DECLARE  @PDATA31 int;
DECLARE  @tag int;
BEGIN TRY
    BEGIN TRANSACTION

DECLARE  cur_data  cursor  for
--Select  distinct PRODUCTIONLINE,PRODUCTIONLINENAME ,FPRODUCTIONLINE,PSEQ from PMC_PP_STATION

Select  distinct PRODUCTIONLINE,PRODUCTIONLINENAME,PSEQ 
from PMC_PP_STATION_PRODUCT
order by PSEQ
 
--select distinct eventdate1,eventdate30,eventdate31 from tabProductHour 
--where CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)

open  cur_data
fetch  next  from  cur_data  into  @PPRODUCTIONLINE ,@PPRODUCTIONLINENAME ,@SEQ
while(@@fetch_status  =  0) 
begin 
 select @tag = COUNT(1) from dbo.PMC_PP_MM  where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME  and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
 if @tag = 0 
 begin	SELECT * FROM PMC_PP_MM
   insert into PMC_PP_MM (YYYY_MM,PRODUCTIONLINE,PRODUCTIONLINENAME,seq)values(SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7),@PPRODUCTIONLINE,@PPRODUCTIONLINENAME,@SEQ)
 end
 --获取排序
 --select @SEQ = SEQ from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and 
 --CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
 
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='01'
begin 
   set @PDATA1 = 0;
  select @PDATA1 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and  EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA1 is null
  begin
   set @PDATA1 = 0;
  end
  update dbo.PMC_PP_MM set DATA1 = @PDATA1,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='02'
begin
  set @PDATA2 = 0;
  select @PDATA2 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA2 is null
  begin
   set @PDATA2 = 0;
  end
  update dbo.PMC_PP_MM set DATA2 = @PDATA2,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='03'
begin
  set @PDATA3 = 0;	
  select @PDATA3 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA3 is null
  begin
   set @PDATA3 = 0;
  end
  update dbo.PMC_PP_MM set DATA3 = @PDATA3,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='04'
begin
  set @PDATA4 = 0;
  select @PDATA4 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA4 is null
  begin
   set @PDATA4 = 0;
  end
  update dbo.PMC_PP_MM set DATA4 = @PDATA4,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='05'
begin
  set @PDATA5 = 0;
  select @PDATA5 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and  EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA5 is null
  begin
   set @PDATA5 = 0;
  end
  update dbo.PMC_PP_MM set DATA5 = @PDATA5,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='06'
begin
  set @PDATA6 = 0;
  select @PDATA6 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and  EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA6 is null
  begin
   set @PDATA6 = 0;
  end
  update dbo.PMC_PP_MM set DATA6 = @PDATA6,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE  AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='07'
begin
  set @PDATA7 = 0;
  select @PDATA7 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA7 is null
  begin
   set @PDATA7 = 0;
  end
  update dbo.PMC_PP_MM set DATA7 = @PDATA7,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE  AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='08'
begin
  set @PDATA8 = 0;
  select @PDATA8 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA8 is null
  begin
   set @PDATA8 = 0;
  end
  update dbo.PMC_PP_MM set DATA8 = @PDATA8,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE  AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='09'
begin
  set @PDATA9 = 0;
  select @PDATA9 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA9 is null
  begin
   set @PDATA9 = 0;
  end
  update dbo.PMC_PP_MM set DATA9 = @PDATA9,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE  AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='10'
begin
  set @PDATA10 = 0;
  select @PDATA10 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA10 is null
  begin
   set @PDATA10 = 0;
  end
  update dbo.PMC_PP_MM set DATA10 = @PDATA10,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='11'
begin
  set @PDATA11 = 0;
  select @PDATA11 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA11 is null
  begin
   set @PDATA11 = 0;
  end
  update dbo.PMC_PP_MM set DATA11 = @PDATA11,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='12'
begin
   set @PDATA12 = 0;
  select @PDATA12 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA12 is null
  begin
   set @PDATA12 = 0;
  end
  update dbo.PMC_PP_MM set DATA12 = @PDATA12,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='13'
begin
  set @PDATA13 = 0;
  select @PDATA13 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA13 is null
  begin
   set @PDATA13 = 0;
  end
  update dbo.PMC_PP_MM set DATA13 = @PDATA13,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='14'
begin
  set @PDATA14 = 0;
  select @PDATA14 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA14 is null
  begin
   set @PDATA14 = 0;
  end
  update dbo.PMC_PP_MM set DATA14 = @PDATA14,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='15'
begin
  set @PDATA15 = 0;
  select @PDATA15 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA15 is null
  begin
   set @PDATA15 = 0;
  end
  update dbo.PMC_PP_MM set DATA15 = @PDATA15,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='16'
begin
  set @PDATA16 = 0;
  select @PDATA16 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA16 is null
  begin
   set @PDATA16 = 0;
  end
  update dbo.PMC_PP_MM set DATA16 = @PDATA16,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='17'
begin
  set @PDATA17 = 0;
  select @PDATA17 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA17 is null
  begin
   set @PDATA17 = 0;
  end
  update dbo.PMC_PP_MM set DATA17 = @PDATA17,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='18'
begin
  set @PDATA18 = 0;
  select @PDATA18 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA18 is null
  begin
   set @PDATA18 = 0;
  end
  update dbo.PMC_PP_MM set DATA18 = @PDATA18,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='19'
begin
  set @PDATA19 = 0;
  select @PDATA19 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA19 is null
  begin
   set @PDATA19 = 0;
  end
  update dbo.PMC_PP_MM set DATA19 = @PDATA19,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='20'
begin
  set @PDATA20 = 0;
  select @PDATA20 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA20 is null
  begin
   set @PDATA20 = 0;
  end
  update dbo.PMC_PP_MM set DATA20 = @PDATA20,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='21'
begin
 set @PDATA21 = 0;
  select @PDATA21 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA21 is null
  begin
   set @PDATA21 = 0;
  end
  update dbo.PMC_PP_MM set DATA21 = @PDATA21,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='22'
begin
  set @PDATA22 = 0;
  select @PDATA22 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA22 is null
  begin
   set @PDATA22 = 0;
  end
  update dbo.PMC_PP_MM set DATA22 = @PDATA22,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='23'
begin
  set @PDATA23 = 0;
  select @PDATA23 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE and  EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA23 is null
  begin
   set @PDATA23 = 0;
  end
  update dbo.PMC_PP_MM set DATA23= @PDATA23,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='24'
begin
  set @PDATA24 = 0;
  select @PDATA24 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA24 is null
  begin
   set @PDATA24 = 0;
  end
  update dbo.PMC_PP_MM set DATA24 = @PDATA24,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='25'
begin
  set @PDATA25 = 0;
  select @PDATA25 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE and  EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA25 is null
  begin
   set @PDATA25 = 0;
  end
  update dbo.PMC_PP_MM set DATA25 = @PDATA25,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='26'
begin
  set @PDATA26 = 0;
  select @PDATA26 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA26 is null
  begin
   set @PDATA26 = 0;
  end
  update dbo.PMC_PP_MM set DATA26 = @PDATA26,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='27'
begin
  set @PDATA27 = 0;
  select @PDATA27 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE and  EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA27 is null
  begin
   set @PDATA27 = 0;
  end
  update dbo.PMC_PP_MM set DATA27 = @PDATA27,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='28'
begin
  set @PDATA28 = 0;
  select @PDATA28 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA28 is null
  begin
   set @PDATA28 = 0;
  end
  update dbo.PMC_PP_MM set DATA28 = @PDATA28,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='29'
begin
   set @PDATA29 = 0;
  select @PDATA29 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE and  EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA29 is null
  begin
   set @PDATA29 = 0;
  end
  update dbo.PMC_PP_MM set DATA29 = @PDATA29,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='30'
begin
  set @PDATA30 = 0;
  select @PDATA30 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA30 is null
  begin
   set @PDATA30 = 0;
  end
  update dbo.PMC_PP_MM set DATA30 = @PDATA30,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end

if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),9,10) ='31'
begin
  set @PDATA31 = 0;
  select @PDATA31 = EventDate29 from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
  if @PDATA31 is null
  begin
   set @PDATA31 = 0;
  end
  update dbo.PMC_PP_MM set DATA31 = @PDATA31,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE AND PRODUCTIONLINENAME = @PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
end

------------总实际产量,查询该月第一天到最后一天的产量
select @PMONTHREALCOUNT = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PMONTHREALCOUNT is null 
  begin
   set @PMONTHREALCOUNT = 0
  end
------------总计划产量
--SELECT @PMONTHPLANCOUNT = SUM(PRODUCTTOTAL) FROM PMC_DATE_IMPORT 
--WHERE SUBSTRING(CONVERT(varchar(100), WORKDATE, 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)

SELECT @PMONTHPLANCOUNT = SUM(CONVERT(INT,EventDate31)) FROM tabProductHour 
where  EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)

--if @PPRODUCTIONLINE = 'AP13BSL'or @PPRODUCTIONLINE = 'AP13BSR'
--select @PMONTHPLANCOUNT = SUM(PLANMODEL3)
--	from dbo.PMC_DATE_IMPORT  where 
--	SUBSTRING(CONVERT(varchar(100), convert(datetime, WORKDATE), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
--ELSE if  @PPRODUCTIONLINE = 'BSL'or @PPRODUCTIONLINE = 'BSR'
--select @PMONTHPLANCOUNT = SUM(PLANMODEL1)+ SUM(PLANMODEL2)
--	from dbo.PMC_DATE_IMPORT  where 
--	SUBSTRING(CONVERT(varchar(100), convert(datetime, WORKDATE), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
--else
--select @PMONTHPLANCOUNT = SUM(PLANMODEL1)+SUM(PLANMODEL2)+SUM(PLANMODEL3)
--	from dbo.PMC_DATE_IMPORT  where 
--	SUBSTRING(CONVERT(varchar(100), convert(datetime, WORKDATE), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)

--if @PMONTHPLANCOUNT =0
--begin
-- set @PMONTHPLANCOUNT = 1
--end
------------计划完成率
if @PMONTHPLANCOUNT = 0
begin
	set @PMONTHRATE = 0
end
if @PMONTHPLANCOUNT <> 0
begin
	set @PMONTHRATE = @PMONTHREALCOUNT/(@PMONTHPLANCOUNT+0.0)*100 
end
set @VARCHARPMONTHRATE =CAST(@PMONTHRATE as varchar)+'%';
update dbo.PMC_PP_MM set MMRATE = @VARCHARPMONTHRATE,MMREALP = @PMONTHREALCOUNT ,MMPLANP = @PMONTHPLANCOUNT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY_MM = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
   
fetch  next  from  cur_data  into  @PPRODUCTIONLINE ,@PPRODUCTIONLINENAME,@SEQ
end
close  cur_data
DEALLOCATE  cur_data

COMMIT TRANSACTION
  END TRY
  BEGIN CATCH
    
    IF XACT_STATE() <> 0
    BEGIN
      ROLLBACK TRANSACTION
    END
    
    SET @ERROR_TAG = '1'
    SET @ERROR_MSG = ERROR_MESSAGE()    
   
  END CATCH

END
