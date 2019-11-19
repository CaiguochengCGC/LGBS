USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_KPI_DAY]    Script Date: 08/24/2015 09:47:56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

--每天早8点16分运行
-----KPI日报表存储过程    
ALTER  PROCEDURE  [dbo].[USP_GET_PMC_KPI_DAY] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --错误标志：0.成功,1.错误
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --错误信息
As 
Begin
DECLARE  @IS_WORK INT;
DECLARE  @SEQ	INT;--顺序
DECLARE  @PPRODUCTIONLINE  varchar(1024) ;--线体
DECLARE  @PPRODUCTIONLINENAME   varchar(1024) ;---线体名称 
DECLARE  @PSTOPTIME  int;---设备停机总时间
DECLARE  @PREALTIME  int;---设备运行总时间
DECLARE  @PPLANTIME  int;---总时间
DECLARE  @PSTOPCPUNT  int;---停机次数
DECLARE  @PMTTR  int;---平均故障修复时间
DECLARE  @PMTBF  int;---平均故障发生间隔

DECLARE  @PEQMRATE  numeric(10,2);----设备利用率
DECLARE @VARCHARPEQMRATE  varchar(1024) ;
DECLARE  @PEQPSTOP  numeric(10,2);----设备停机率
DECLARE @VARCHARPEQPSTOP  varchar(1024) ;
BEGIN TRY
    BEGIN TRANSACTION
--获取工作日历
SELECT @IS_WORK = COUNT(1) FROM PMC_DATE_IMPORT
WHERE CONVERT(VARCHAR(10),WORKDATE,23) = CONVERT(VARCHAR(10),GETDATE()-1,23)
IF @IS_WORK > 0
begin

DECLARE  cur_data  cursor  for
Select  distinct PRODUCTIONLINE,PRODUCTIONLINENAME,PSEQ from PMC_PP_STATION   
--select distinct PRODUCTIONLINE,PRODUCTIONLINENAME,SEQ from PMC_EQUIPMENT_STOPLINE 
--where CONVERT(varchar(100), convert(datetime, PPDATE), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  
--and CONVERT(varchar(100), convert(datetime, PPDATE), 20) <= CONVERT(varchar(100), getdate(), 20);

open  cur_data
fetch  next  from  cur_data  into  @PPRODUCTIONLINE ,@PPRODUCTIONLINENAME,@SEQ
while(@@fetch_status  =  0) 
begin 
   set @PSTOPTIME = 0 ;
   set @PSTOPCPUNT = 0 ;
   set @PREALTIME = 0 ;
   set @PPLANTIME = 0 ;
   set @PMTTR = 0 ;
   set @PMTBF = 0 ;
   set @PEQMRATE = 0 ;
   set @PEQPSTOP = 0 ;
   set @VARCHARPEQMRATE = 0 ;
   set @VARCHARPEQPSTOP = 0 ;
   select @PSTOPTIME = STOPTIME,  @PSTOPCPUNT = STOPCOUNT    from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and CONVERT(varchar(100), convert(datetime, PPDATE), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, PPDATE), 20) <= CONVERT(varchar(100), getdate(), 20);
---设备运行总时间
--SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,dateadd(day,-1,getdate()),dateadd(day,-1,getdate()));

--计算设备运行总时间（小时）
SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
WHERE PRODUCTLINE = @PPRODUCTIONLINE AND PRODUCTLINENAME = @PPRODUCTIONLINENAME
AND CONVERT(VARCHAR(10),PPDATE,20) = CONVERT(varchar(10), dateadd(day,-1,getdate()), 20)
if @PREALTIME is null
	SET @PREALTIME = 0;
	
---总时间
SET @PPLANTIME  = dbo.USF_GET_PLANTIME(dateadd(day,-1,getdate()),dateadd(day,-1,getdate()));

--if @PPLANTIME is null
--   SET @PPLANTIME = 1;
 ---平均故障修复时间MTTR
 if (@PSTOPCPUNT != 0)and (@PSTOPCPUNT is not null)and(@PSTOPTIME is not null) 
 begin
 SET @PMTTR  =round(@PSTOPTIME/@PSTOPCPUNT,0);
---平均故障发生间隔MTBF
 SET @PMTBF  =round(@PREALTIME/@PSTOPCPUNT,0);
 end
 if @PPLANTIME!=0 and @PPLANTIME is not null and @PREALTIME is not null and @PREALTIME !=0
 begin
 ----设备利用率
 SET @PEQMRATE =@PREALTIME/(@PPLANTIME+0.0)*100 ;
 SET @VARCHARPEQMRATE =cast(@PEQMRATE as varchar) ;
 end
 ----设备停机率
 IF @PREALTIME != 0 and @PREALTIME is not null and @PSTOPTIME is not null and @PSTOPTIME !=0
 begin
 SET @PEQPSTOP =@PSTOPTIME/(@PREALTIME+0.0)*100 ;
SET @VARCHARPEQPSTOP = cast(@PEQPSTOP as varchar) ;
 end 
INSERT  into PMC_PP_KPIDAY(PPDATE,PRODUCTIONLINE,PRODUCTIONLINENAME,MTTR,MTBF,EQMRATE,EQPSTOP,STOPTIME,STOPCOUNT,OPERUSER)values
 (dateadd(day,-1,getdate()),@PPRODUCTIONLINE,@PPRODUCTIONLINENAME,@PMTTR,@PMTBF,@VARCHARPEQMRATE,@VARCHARPEQPSTOP,@PSTOPTIME,@PSTOPCPUNT,@SEQ)
 
fetch  next  from  cur_data  into  @PPRODUCTIONLINE ,@PPRODUCTIONLINENAME,@SEQ
end
close  cur_data
DEALLOCATE  cur_data
END
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