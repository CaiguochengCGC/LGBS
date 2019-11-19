USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_EQUIPMENTSTOPLINE]    Script Date: 08/24/2015 09:47:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER  PROCEDURE  [dbo].[USP_GET_PMC_EQUIPMENTSTOPLINE] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --错误标志：0.成功,1.错误
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --错误信息
As 
Begin
DECLARE  @IS_WORK	INT;--是否是工作时间
DECLARE  @PSTATION   varchar(1024) ;
DECLARE  @FPSTATION   varchar(1024) ;---线体归类
DECLARE  @PSTATIONNAME   varchar(1024) ;---工段名字
DECLARE	 @PSEQ	INT;	--排序
DECLARE  @PSTOPTIME  int;
DECLARE  @PSTOPCOUNT int;
DECLARE  @PSTOPTIMEM int;
DECLARE  @PDATA3  int;----
DECLARE  @PDATA4 int;
DECLARE  @PDATA5 int;
DECLARE  @PDATA6  int;
DECLARE  @PDATA7 int;

BEGIN TRY
    BEGIN TRANSACTION
--获取工作日历
SELECT @IS_WORK = COUNT(1) FROM PMC_DATE_IMPORT
WHERE CONVERT(VARCHAR(10),WORKDATE,23) = CONVERT(VARCHAR(10),GETDATE()-1,23)
IF @IS_WORK > 0
begin
DECLARE  cur_data  cursor  for
Select  distinct PRODUCTIONLINE,PRODUCTIONLINENAME,FPRODUCTIONLINE,PSEQ from PMC_PP_STATION 

open  cur_data
fetch  next  from  cur_data  into  @PSTATION, @PSTATIONNAME ,@FPSTATION,@PSEQ
while(@@fetch_status = 0) 
begin 
Select  
@PSTOPTIME = SUM(datediff(ss, EventDate1, EventDate2)) , 
@PSTOPCOUNT = count(EventData40)  
from  [dbo].[tabStopLine] 
where CONVERT(varchar(100), convert(datetime, EventDate1), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)
and CONVERT(varchar(100), convert(datetime, EventDate1), 20) <= CONVERT(varchar(100), getdate(), 20)
and EventData46 = @PSTATION;

SET @PSTOPTIMEM = convert(int, @PSTOPTIME/60)----次数;
------- EventData3的数据
Select @PDATA3 = SUM(datediff(ss, EventDate1, EventDate2)) from  [dbo].[tabStopLine] 
  where  EventData46 = @PSTATION and   EventData3!='' and EventData3 is not null  and 
CONVERT(varchar(100), EventDate1, 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  
and CONVERT(varchar(100), EventDate1, 20) <= CONVERT(varchar(100), getdate(), 20)

if  @PDATA3 is null 
SET @PDATA3 = 0
else
SET @PDATA3 =  convert(int, @PDATA3/60)
------- EventData4的数据
Select @PDATA4 = SUM(datediff(ss, EventDate1, EventDate2)) from  [dbo].[tabStopLine] 
   where  EventData46 = @PSTATION and   EventData4!=''  and EventData4 is not null  and 
CONVERT(varchar(100), EventDate1, 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  
and CONVERT(varchar(100), EventDate1, 20) <= CONVERT(varchar(100), getdate(), 20)
if  @PDATA4 is null 
SET @PDATA4 = 0
else
SET @PDATA4 =  convert(int, @PDATA4/60)
------- EventData5的数据
Select @PDATA5 = SUM(datediff(ss, EventDate1, EventDate2)) from  [dbo].[tabStopLine] 
   where  EventData46 = @PSTATION and   EventData5!='' and EventData5 is not null  and 
CONVERT(varchar(100), EventDate1, 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  
and CONVERT(varchar(100), EventDate1, 20) <= CONVERT(varchar(100), getdate(), 20)
if  @PDATA5 is null 
SET @PDATA5 = 0
else
SET @PDATA5 =  convert(int, @PDATA5/60)
------- EventData6的数据
Select @PDATA6 =  SUM(datediff(ss, EventDate1, EventDate2)) from  [dbo].[tabStopLine] 
   where  EventData46 = @PSTATION and   EventData6!=''  and EventData6 is not null and 
CONVERT(varchar(100), EventDate1, 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  
and CONVERT(varchar(100), EventDate1, 20) <= CONVERT(varchar(100), getdate(), 20)
if  @PDATA6 is null 
SET @PDATA6 = 0
else
SET @PDATA6 =  convert(int, @PDATA6/60)
------- EventData7的数据
Select @PDATA7 = SUM(datediff(ss, EventDate1, EventDate2)) from  [dbo].[tabStopLine] 
   where  EventData46 = @PSTATION and   EventData7!=''  and   EventData7 is not null   and 
CONVERT(varchar(100), EventDate1, 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  
and CONVERT(varchar(100), EventDate1, 20) <= CONVERT(varchar(100), getdate(), 20)
if  @PDATA7 is null 
SET @PDATA7 = 0
else
SET @PDATA7 =  convert(int, @PDATA7/60)
---------将数据插入表工段停线报表PMC_EQUIPMENT_STOPLINE表中。
Insert into  dbo.PMC_EQUIPMENT_STOPLINE (PPDATE, FPRODUCTIONLINE, PRODUCTIONLINE, PRODUCTIONLINENAME, STOPTIME, STOPCOUNT, DATA3, DATA4, DATA5, DATA6, DATA7, SEQ) values (dateadd(day,-1,DATEADD(MI,20,getdate())),@FPSTATION, @PSTATION, @PSTATIONNAME, @PSTOPTIMEM, @PSTOPCOUNT, @PDATA3, @PDATA4, @PDATA5, @PDATA6, @PDATA7, @PSEQ)

fetch  next  from  cur_data  into @PSTATION, @PSTATIONNAME ,@FPSTATION,@PSEQ
end
close  cur_data
DEALLOCATE  cur_data
end
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
