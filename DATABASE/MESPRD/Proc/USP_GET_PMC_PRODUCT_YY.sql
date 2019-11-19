USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_PRODUCT_YY]    Script Date: 08/24/2015 09:49:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-----产量年报表存储过程   
------每月的1号早上8：09执行上一个月的数据 
ALTER  PROCEDURE  [dbo].[USP_GET_PMC_PRODUCT_YY] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --错误标志：0.成功,1.错误
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --错误信息
As 
Begin
DECLARE  @PPRODUCTIONLINE  varchar(1024) ;--线体
DECLARE  @PPRODUCTIONLINENAME   varchar(1024) ;---线体名称 
--DECLARE  @PFRODUCTIONLINE   varchar(1024) ;---线体归类 
DECLARE  @PMONTHPLANCOUNT  int;--本年计划产量
DECLARE  @PMONTHREALCOUNT  int;--本年实际产量
DECLARE  @SEQ	int;--排序规则
DECLARE  @PMONTHRATE   numeric(6,2) ;--本年完成率
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
DECLARE  @tag int;
BEGIN TRY
    BEGIN TRANSACTION

DECLARE  cur_data  cursor  for
--Select  distinct PRODUCTIONLINE,PRODUCTIONLINENAME ,FPRODUCTIONLINE,PSEQ from PMC_PP_STATION   

Select  distinct PRODUCTIONLINE,PRODUCTIONLINENAME,PSEQ 
from PMC_PP_STATION_PRODUCT
order by PSEQ

--select distinct eventdate1,eventdate30,eventdate31 from tabProductHour 
--where SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)

open  cur_data
fetch  next  from  cur_data  into  @PPRODUCTIONLINE ,@PPRODUCTIONLINENAME,@SEQ
while(@@fetch_status  =  0) 
begin 
 select @tag = COUNT(1) from dbo.PMC_PP_YY  where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME  and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)
 if @tag = 0 
 begin
   insert into PMC_PP_YY (YYYY,PRODUCTIONLINE,PRODUCTIONLINENAME,seq)values(SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4),@PPRODUCTIONLINE,@PPRODUCTIONLINENAME,@SEQ)
 end
 --获取排序规则
 --select @SEQ = SEQ from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and 
 --CONVERT(varchar(100), convert(datetime, EventData), 23) = CONVERT(varchar(100), dateadd(day,-1,getdate()), 23)
 
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='01'
begin 
  set @PDATA1 = 0;
  select @PDATA1 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PDATA1 is null
  begin
   set @PDATA1 = 0;
  end
  update dbo.PMC_PP_YY set DATA1 = @PDATA1,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='02'
begin 
   set @PDATA2 = 0;
  select @PDATA2 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PDATA2 is null
  begin
   set @PDATA2 = 0;
  end
  update dbo.PMC_PP_YY set DATA2 = @PDATA2,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='03'
begin 
   set @PDATA3 = 0;
  select @PDATA3 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PDATA3 is null
  begin
   set @PDATA3 = 0;
  end
  update dbo.PMC_PP_YY set DATA3 = @PDATA3,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='04'
begin 
   set @PDATA4 = 0;
  select @PDATA4 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PDATA4 is null
  begin
   set @PDATA4 = 0;
  end
  update dbo.PMC_PP_YY set DATA4 = @PDATA4,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='05'
begin 
   set @PDATA5 = 0;
  select @PDATA5 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PDATA5 is null
  begin
   set @PDATA5 = 0;
  end
  update dbo.PMC_PP_YY set DATA5 = @PDATA5,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='06'
begin 
   set @PDATA6 = 0;
  select @PDATA6 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PDATA6 is null
  begin
   set @PDATA6 = 0;
  end
  update dbo.PMC_PP_YY set DATA6 = @PDATA6,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='07'
begin 
   set @PDATA7 = 0;
  select @PDATA7 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PDATA7 is null
  begin
   set @PDATA7 = 0;
  end
  update dbo.PMC_PP_YY set DATA7 = @PDATA7,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='08'
begin 
   set @PDATA8= 0;
  select @PDATA8 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PDATA8 is null
  begin
   set @PDATA8= 0;
  end
  update dbo.PMC_PP_YY set DATA8 = @PDATA8,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='09'
begin 
  set @PDATA9 = 0;
  select @PDATA9 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PDATA9 is null
  begin
   set @PDATA9 = 0;
  end
  update dbo.PMC_PP_YY set DATA9 = @PDATA9,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='10'
begin 
 set @PDATA10 = 0;
  select @PDATA10 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PDATA10 is null
  begin
   set @PDATA10 = 0;
  end
  update dbo.PMC_PP_YY set DATA10 = @PDATA10,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='11'
begin 
   set @PDATA11 = 0;
  select @PDATA11 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PDATA11 is null
  begin
   set @PDATA11 = 0;
  end
  update dbo.PMC_PP_YY set DATA11 = @PDATA11,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='12'
begin 
    set @PDATA12 = 0;
  select @PDATA12 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,7) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7)
  if @PDATA12 is null
  begin
   set @PDATA12 = 0;
  end
  update dbo.PMC_PP_YY set DATA12 = @PDATA12,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY= SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end



------------总实际产量,查询该年产量
select @PMONTHREALCOUNT = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)
  if @PMONTHREALCOUNT is null 
  begin
   set @PMONTHREALCOUNT = 0
  end
------------总计划产量
--SELECT @PMONTHPLANCOUNT = SUM(PRODUCTTOTAL) FROM PMC_DATE_IMPORT 
--WHERE SUBSTRING(CONVERT(varchar(100), WORKDATE, 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)

SELECT @PMONTHPLANCOUNT = SUM(CONVERT(INT,EventDate31)) FROM tabProductHour 
where  EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
SUBSTRING(CONVERT(varchar(100), EventData, 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)

------------计划完成率
if @PMONTHPLANCOUNT = 0
begin
	set @PMONTHRATE = 0
end
if @PMONTHPLANCOUNT <> 0
begin
	set @PMONTHRATE = @PMONTHREALCOUNT/(@PMONTHPLANCOUNT+0.0)*100 
end
--set @PMONTHRATE =@PMONTHRATE+'%';
set @VARCHARPMONTHRATE =CAST(@PMONTHRATE as varchar )+'%';
update dbo.PMC_PP_YY set YYRATE = @VARCHARPMONTHRATE,YYREALP = @PMONTHREALCOUNT ,YYPLANP = @PMONTHPLANCOUNT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 

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