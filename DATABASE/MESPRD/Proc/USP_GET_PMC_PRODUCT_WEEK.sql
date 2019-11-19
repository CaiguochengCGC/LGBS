USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_PRODUCT_WEEK]    Script Date: 08/24/2015 09:49:34 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

---按照自然周报表， 每周天是一周的开始，所以在每周天的早上8：11分运行
-----产量周报表存储过程    
ALTER  PROCEDURE  [dbo].[USP_GET_PMC_PRODUCT_WEEK] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --错误标志：0.成功,1.错误
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --错误信息
As 
Begin
DECLARE  @PPRODUCTIONLINE  varchar(1024) ;--线体
DECLARE  @PPRODUCTIONLINENAME   varchar(1024) ;---线体名称 
--DECLARE  @PFRODUCTIONLINE   varchar(1024) ;---线体归类 
DECLARE  @PMONTHPLANCOUNT  int;--本年计划产量
DECLARE  @PMONTHREALCOUNT  int;--本年实际产量
DECLARE	 @SEQ	int;---排序规则
DECLARE  @PMONTHRATE   numeric(6,2) ;--本月完成率
DECLARE  @VARCHARPMONTHRATE  varchar(1024) ;
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
DECLARE  @PDATA32 int;
DECLARE  @PDATA33  int;----
DECLARE  @PDATA34 int;
DECLARE  @PDATA35 int;
DECLARE  @PDATA36  int;
DECLARE  @PDATA37 int;
DECLARE  @PDATA38  int;
DECLARE  @PDATA39 int;
DECLARE  @PDATA40 int;
DECLARE  @PDATA41 int;
DECLARE  @PDATA42 int;
DECLARE  @PDATA43  int;----
DECLARE  @PDATA44 int;
DECLARE  @PDATA45 int;
DECLARE  @PDATA46  int;
DECLARE  @PDATA47 int;
DECLARE  @PDATA48  int;
DECLARE  @PDATA49 int;
DECLARE  @PDATA50 int;
DECLARE  @PDATA51 int;
DECLARE  @PDATA52 int;
DECLARE  @PDATA53  int;
DECLARE  @tag int;
BEGIN TRY
    BEGIN TRANSACTION

DECLARE  cur_data  cursor  for
--Select  distinct PRODUCTIONLINE,PRODUCTIONLINENAME ,FPRODUCTIONLINE,PSEQ from PMC_PP_STATION  

Select  distinct PRODUCTIONLINE,PRODUCTIONLINENAME,PSEQ 
from PMC_PP_STATION_PRODUCT
order by PSEQ
 
--select distinct eventdate1,eventdate30,eventdate31 from tabProductHour 
--where datepart(week,dateadd(day,-1,convert(datetime, EventData))) = datepart(week,dateadd(day,-1,convert(datetime, getdate())))
--and SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)

open  cur_data
fetch  next  from  cur_data  into  @PPRODUCTIONLINE ,@PPRODUCTIONLINENAME,@SEQ
while(@@fetch_status  =  0) 
begin 
 select @tag = COUNT(1) from dbo.PMC_PP_WEEK  where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME  and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)
 if @tag = 0 
 begin
   insert into PMC_PP_WEEK(YYYY,PRODUCTIONLINE,PRODUCTIONLINENAME,seq)values(SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4),@PPRODUCTIONLINE,@PPRODUCTIONLINENAME,@SEQ)
 end
  
 -------第一周
if datepart(week,dateadd(day,-1,getdate())) ='1'
begin 
    set @PDATA1 = 0;
  select @PDATA1 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '1' 
  if @PDATA1 is null
  begin
   set @PDATA1 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA1 = @PDATA1,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第2周
if datepart(week,dateadd(day,-1,getdate())) ='2'
begin 
  set @PDATA2 = 0;
  select @PDATA2 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '2' 
  if @PDATA2 is null
  begin
   set @PDATA2 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA2 = @PDATA2,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第3周
if datepart(week,dateadd(day,-1,getdate())) ='3'
begin 
   set @PDATA3 = 0;
  select @PDATA3 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '3' 
  if @PDATA3 is null
  begin
   set @PDATA3 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA3 = @PDATA3,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第4周
if datepart(week,dateadd(day,-1,getdate())) ='4'
begin 
    set @PDATA4 = 0;
  select @PDATA4 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '4' 
  if @PDATA4 is null
  begin
   set @PDATA4 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA4 = @PDATA4,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第5周
if datepart(week,dateadd(day,-1,getdate())) ='5'
begin 
  set @PDATA5 = 0;
  select @PDATA5 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '5' 
  if @PDATA5 is null
  begin
   set @PDATA5 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA5 = @PDATA5,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第6周
if datepart(week,dateadd(day,-1,getdate())) ='6'
begin 
   set @PDATA6 = 0;
  select @PDATA6 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '6' 
  if @PDATA6 is null
  begin
   set @PDATA6 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA6 = @PDATA6,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第7周
if datepart(week,dateadd(day,-1,getdate())) ='7'
begin 
  set @PDATA7 = 0;
  select @PDATA7 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '7' 
  if @PDATA7 is null
  begin
   set @PDATA7 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA7 = @PDATA7,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第8周
if datepart(week,dateadd(day,-1,getdate())) ='8'
begin 
   set @PDATA8 = 0;
  select @PDATA8 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '8' 
  if @PDATA8 is null
  begin
   set @PDATA8 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA8 = @PDATA8,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第9周
if datepart(week,dateadd(day,-1,getdate())) ='9'
begin 
  set @PDATA9 = 0;
  select @PDATA9 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '9' 
  if @PDATA9 is null
  begin
   set @PDATA9 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA9 = @PDATA9,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第10周
if datepart(week,dateadd(day,-1,getdate())) ='10'
begin 
   set @PDATA10 = 0;
  select @PDATA10 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '10' 
  if @PDATA10 is null
  begin
   set @PDATA10 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA10 = @PDATA10,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end


 -------第11周
if datepart(week,dateadd(day,-1,getdate())) ='11'
begin 
  set @PDATA11 = 0;
  select @PDATA11 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '11' 
  if @PDATA11 is null
  begin
   set @PDATA11 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA11 = @PDATA11,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第12周
if datepart(week,dateadd(day,-1,getdate())) ='12'
begin 
  set @PDATA12 = 0;
  select @PDATA12 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '12' 
  if @PDATA12 is null
  begin
   set @PDATA12 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA12 = @PDATA12,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第13周
if datepart(week,dateadd(day,-1,getdate())) ='13'
begin 
  set @PDATA13 = 0;
  select @PDATA13 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '13' 
  if @PDATA13 is null
  begin
   set @PDATA13 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA13 = @PDATA13,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第14周
if datepart(week,dateadd(day,-1,getdate())) ='14'
begin 
  set @PDATA14 = 0;
  select @PDATA14 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '14' 
  if @PDATA14 is null
  begin
   set @PDATA14 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA14 = @PDATA14,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第15周
if datepart(week,dateadd(day,-1,getdate())) ='15'
begin 
   set @PDATA15 = 0;
  select @PDATA15 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '15' 
  if @PDATA15 is null
  begin
   set @PDATA15 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA15 = @PDATA15,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第16周
if datepart(week,dateadd(day,-1,getdate())) ='16'
begin 
  set @PDATA16 = 0;
  select @PDATA16 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '16' 
  if @PDATA16 is null
  begin
   set @PDATA16 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA16 = @PDATA16,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE 
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第17周
if datepart(week,dateadd(day,-1,getdate())) ='17'
begin 
  set @PDATA17 = 0;
  select @PDATA17 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '17' 
  if @PDATA17 is null
  begin
   set @PDATA17 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA17 = @PDATA17,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第18周
if datepart(week,dateadd(day,-1,getdate())) ='18'
begin 
   set @PDATA18 = 0;
  select @PDATA18 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '18' 
  if @PDATA18 is null
  begin
   set @PDATA18 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA18 = @PDATA18,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第19周
if datepart(week,dateadd(day,-1,getdate())) ='19'
begin 
   set @PDATA19 = 0;
  select @PDATA19 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '19' 
  if @PDATA19 is null
  begin
   set @PDATA19 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA19 = @PDATA19,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第20周
if datepart(week,dateadd(day,-1,getdate())) ='20'
begin 
   set @PDATA20 = 0;
  select @PDATA20 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '20' 
  if @PDATA20 is null
  begin
   set @PDATA20 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA20 = @PDATA20,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end


 -------第21周
if datepart(week,dateadd(day,-1,getdate())) ='21'
begin 
   set @PDATA21 = 0;
  select @PDATA21 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '21' 
  if @PDATA21 is null
  begin
   set @PDATA21 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA21 = @PDATA21,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第22周
if datepart(week,dateadd(day,-1,getdate())) ='22'
begin 
   set @PDATA22 = 0;
  select @PDATA22 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '22' 
  if @PDATA22 is null
  begin
   set @PDATA22 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA22 = @PDATA22,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第23周
if datepart(week,dateadd(day,-1,getdate())) ='23'
begin 
   set @PDATA23 = 0;
  select @PDATA23 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '23' 
  if @PDATA23 is null
  begin
   set @PDATA23 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA23 = @PDATA23,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第24周
if datepart(week,dateadd(day,-1,getdate())) ='24'
begin 
   set @PDATA24 = 0;
  select @PDATA24 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '24' 
  if @PDATA24 is null
  begin
   set @PDATA24 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA24 = @PDATA24,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第25周
if datepart(week,dateadd(day,-1,getdate())) ='25'
begin 
   set @PDATA25 = 0;
  select @PDATA25 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '25' 
  if @PDATA25 is null
  begin
   set @PDATA25 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA25 = @PDATA25,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第26周
if datepart(week,dateadd(day,-1,getdate())) ='26'
begin 
    set @PDATA26 = 0;
  select @PDATA26 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '26' 
  if @PDATA26 is null
  begin
   set @PDATA26 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA26 = @PDATA26,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第27周
if datepart(week,dateadd(day,-1,getdate())) ='27'
begin 
  set @PDATA27 = 0;
  select @PDATA27 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '27' 
  if @PDATA27 is null
  begin
   set @PDATA27 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA27 = @PDATA27,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第28周
if datepart(week,dateadd(day,-1,getdate())) ='28'
begin 
   set @PDATA28 = 0;
  select @PDATA28 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '28' 
  if @PDATA28 is null
  begin
   set @PDATA28 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA28 = @PDATA28,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第29周
if datepart(week,dateadd(day,-1,getdate())) ='29'
begin 
    set @PDATA29 = 0;
  select @PDATA29 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '29' 
  if @PDATA29 is null
  begin
   set @PDATA29 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA29 = @PDATA29,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第30周
if datepart(week,dateadd(day,-1,getdate())) ='30'
begin 
   set @PDATA30 = 0;
  select @PDATA30 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '30' 
  if @PDATA30 is null
  begin
   set @PDATA30 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA30 = @PDATA30,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end


 -------第31周
if datepart(week,dateadd(day,-1,getdate())) ='31'
begin 
    set @PDATA31 = 0;
  select @PDATA31 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '31' 
  if @PDATA31 is null
  begin
   set @PDATA31 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA31 = @PDATA31,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第32周
if datepart(week,dateadd(day,-1,getdate())) ='32'
begin 
   set @PDATA32 = 0;
  select @PDATA32 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '32' 
  if @PDATA32 is null
  begin
   set @PDATA32 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA32 = @PDATA32,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第33周
if datepart(week,dateadd(day,-1,getdate())) ='33'
begin 
    set @PDATA33 = 0;
  select @PDATA33 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '33' 
  if @PDATA33 is null
  begin
   set @PDATA33 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA33 = @PDATA33,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第34周
if datepart(week,dateadd(day,-1,getdate())) ='34'
begin 
  set @PDATA34 = 0;
  select @PDATA34 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '34' 
  if @PDATA34 is null
  begin
   set @PDATA34 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA34 = @PDATA34,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第35周
if datepart(week,dateadd(day,-1,getdate())) ='35'
begin 
   set @PDATA35 = 0;
  select @PDATA35 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '35' 
  if @PDATA35 is null
  begin
   set @PDATA35 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA35 = @PDATA35,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第36周
if datepart(week,dateadd(day,-1,getdate())) ='36'
begin 
   set @PDATA36 = 0;
  select @PDATA36 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '36' 
  if @PDATA36 is null
  begin
   set @PDATA36 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA36 = @PDATA36,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第37周
if datepart(week,dateadd(day,-1,getdate())) ='37'
begin 
   set @PDATA37 = 0;
  select @PDATA37 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '37' 
  if @PDATA37 is null
  begin
   set @PDATA37 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA37 = @PDATA37,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第38周
if datepart(week,dateadd(day,-1,getdate())) ='38'
begin 
   set @PDATA38 = 0;
  select @PDATA38 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '38' 
  if @PDATA38 is null
  begin
   set @PDATA38 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA38 = @PDATA38,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第39周
if datepart(week,dateadd(day,-1,getdate())) ='39'
begin 
  set @PDATA39 = 0;
  select @PDATA39 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '39' 
  if @PDATA39 is null
  begin
   set @PDATA39 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA39 = @PDATA39,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第40周
if datepart(week,dateadd(day,-1,getdate())) ='40'
begin 
  set @PDATA40 = 0;
  select @PDATA40 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '40' 
  if @PDATA40 is null
  begin
   set @PDATA40 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA40 = @PDATA40,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end

 -------第41周
if datepart(week,dateadd(day,-1,getdate())) ='41'
begin 
  set @PDATA41 = 0;
  select @PDATA41 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '41' 
  if @PDATA41 is null
  begin
   set @PDATA41 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA41 = @PDATA41,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第42周
if datepart(week,dateadd(day,-1,getdate())) ='42'
begin 
   set @PDATA42 = 0;
  select @PDATA42 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '42' 
  if @PDATA42 is null
  begin
   set @PDATA42 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA42 = @PDATA42,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第43周
if datepart(week,dateadd(day,-1,getdate())) ='43'
begin 
    set @PDATA43 = 0;
  select @PDATA43 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '43' 
  if @PDATA43 is null
  begin
   set @PDATA43 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA43 = @PDATA43,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第44周
if datepart(week,dateadd(day,-1,getdate())) ='44'
begin 
   set @PDATA44 = 0;
  select @PDATA44 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '44' 
  if @PDATA44 is null
  begin
   set @PDATA44 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA44 = @PDATA44,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第45周
if datepart(week,dateadd(day,-1,getdate())) ='45'
begin 
   set @PDATA45 = 0;
  select @PDATA45 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '45' 
  if @PDATA45 is null
  begin
   set @PDATA45 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA45 = @PDATA45,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第46周
if datepart(week,dateadd(day,-1,getdate())) ='46'
begin 
   set @PDATA46 = 0;
  select @PDATA46 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '46' 
  if @PDATA46 is null
  begin
   set @PDATA46 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA46 = @PDATA46,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第47周
if datepart(week,dateadd(day,-1,getdate())) ='47'
begin 
  set @PDATA47 = 0;
  select @PDATA47 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '47' 
  if @PDATA47 is null
  begin
   set @PDATA47 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA47 = @PDATA47,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第48周
if datepart(week,dateadd(day,-1,getdate())) ='48'
begin 
   set @PDATA48 = 0;
  select @PDATA48 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '48' 
  if @PDATA48 is null
  begin
   set @PDATA48 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA48 = @PDATA48,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第49周
if datepart(week,dateadd(day,-1,getdate())) ='49'
begin 
   set @PDATA49 = 0;
  select @PDATA49 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '49' 
  if @PDATA49 is null
  begin
   set @PDATA49 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA49 = @PDATA49,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
 -------第50周
if datepart(week,dateadd(day,-1,getdate())) ='50'
begin 
  set @PDATA50 = 0;
  select @PDATA50 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '50' 
  if @PDATA50 is null
  begin
   set @PDATA50 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA50 = @PDATA50,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
-------第51周
if datepart(week,dateadd(day,-1,getdate())) ='51'
begin 
   set @PDATA51 = 0;
  select @PDATA51 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '51' 
  if @PDATA51 is null
  begin
   set @PDATA51 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA51 = @PDATA51,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
-------第52周
if datepart(week,dateadd(day,-1,getdate())) ='52'
begin 
  set @PDATA52 = 0;
  select @PDATA52 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '52' 
  if @PDATA52 is null
  begin
   set @PDATA52 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA52 = @PDATA52,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end
-------第53周
if datepart(week,dateadd(day,-1,getdate())) ='53'
begin 
   set @PDATA53 = 0;
  select @PDATA53 = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE and EventDate30 = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) and 
  datepart(week,dateadd(day,-1,convert(datetime, EventData))) = '53' 
  if @PDATA53 is null
  begin
   set @PDATA53 = 0;
  end
  update dbo.PMC_PP_WEEK set DATA53 = @PDATA53,seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end

------------总实际产量,查询该年产量
select @PMONTHREALCOUNT = SUM(convert(int,EventDate29)) from tabProductHour where EventDate1 = @PPRODUCTIONLINE  and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, EventData), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)
  if @PMONTHREALCOUNT is null 
  begin
   set @PMONTHREALCOUNT = 0
  end
------------总计划产量
--SELECT @PMONTHPLANCOUNT = SUM(PRODUCTTOTAL) FROM PMC_DATE_IMPORT 
--WHERE SUBSTRING(CONVERT(varchar(100), WORKDATE, 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,0,getdate()), 23),1,4)

SELECT @PMONTHPLANCOUNT = SUM(CONVERT(INT,EventDate31)) FROM tabProductHour 
where  EventDate1 = @PPRODUCTIONLINE  and EventDate30 = @PPRODUCTIONLINENAME AND
SUBSTRING(CONVERT(varchar(100), EventData, 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)


--if @PPRODUCTIONLINE = 'AP13BSL'or @PPRODUCTIONLINE = 'AP13BSR'
--select @PMONTHPLANCOUNT = SUM(PLANMODEL3)
--	from dbo.PMC_DATE_IMPORT  where 
--	SUBSTRING(CONVERT(varchar(100), convert(datetime, WORKDATE), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,0,getdate()), 23),1,4)
--ELSE if  @PPRODUCTIONLINE = 'BSL'or @PPRODUCTIONLINE = 'BSR'
--select @PMONTHPLANCOUNT = SUM(PLANMODEL1)+ SUM(PLANMODEL2)
--	from dbo.PMC_DATE_IMPORT  where 
--	SUBSTRING(CONVERT(varchar(100), convert(datetime, WORKDATE), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,0,getdate()), 23),1,4)
--else
--select @PMONTHPLANCOUNT = SUM(PLANMODEL1)+ SUM(PLANMODEL2) + SUM(PLANMODEL3)
--	from dbo.PMC_DATE_IMPORT  where 
--	SUBSTRING(CONVERT(varchar(100), convert(datetime, WORKDATE), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,0,getdate()), 23),1,4)
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
set @VARCHARPMONTHRATE =CAST(@PMONTHRATE as varchar )+'%';
update dbo.PMC_PP_WEEK set WEEKRATE = @VARCHARPMONTHRATE,YYREALP = @PMONTHREALCOUNT ,YYPLANP = @PMONTHPLANCOUNT where PRODUCTIONLINE = @PPRODUCTIONLINE 
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