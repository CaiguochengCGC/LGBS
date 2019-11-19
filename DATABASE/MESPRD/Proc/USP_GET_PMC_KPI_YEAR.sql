USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_KPI_YEAR]    Script Date: 08/24/2015 09:48:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-----KPI年报表存储过程  
-----每年1月1号早上8：18运行上一年的kpi报表的数据
ALTER  PROCEDURE  [dbo].[USP_GET_PMC_KPI_YEAR] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --错误标志：0.成功,1.错误
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --错误信息
As 
Begin
DECLARE  @SEQ	int;--顺序
DECLARE  @PPRODUCTIONLINE  varchar(1024) ;--线体
DECLARE  @PPRODUCTIONLINENAME   varchar(1024) ;---线体名称 
DECLARE  @PSTOPTIME  int;---设备停机总时间
DECLARE  @PREALTIME  int;---设备运行总时间
DECLARE  @PPLANTIME  int;---总运行时间
DECLARE  @PSTOPCPUNT  int;---停机次数
DECLARE  @PMTTR  int;---平均故障修复时间
DECLARE  @PMTBF  int;---平均故障发生间隔

DECLARE  @PEQMRATE  numeric(10,2);----设备利用率
DECLARE @VARCHARPEQMRATE  varchar(1024) ;
DECLARE  @PEQPSTOP  numeric(10,2);----设备停机率
DECLARE @VARCHARPEQPSTOP  varchar(1024) ;
DECLARE  @TAG         INT;

BEGIN TRY
    BEGIN TRANSACTION

DECLARE  cur_data  cursor  for
Select  distinct PRODUCTIONLINE,PRODUCTIONLINENAME,PSEQ from PMC_PP_STATION   
--select distinct PRODUCTIONLINE,PRODUCTIONLINENAME from PMC_EQUIPMENT_STOPLINE 
--where SUBSTRING(CONVERT(varchar(100), PPDATE, 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(year,-1,getdate()), 23),1,4)

open  cur_data
fetch  next  from  cur_data  into  @PPRODUCTIONLINE ,@PPRODUCTIONLINENAME ,@SEQ
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
   select    @PSTOPTIME = SUM(STOPTIME),  @PSTOPCPUNT = SUM(STOPCOUNT)    from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and SUBSTRING(CONVERT(varchar(100), PPDATE, 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(year,-1,getdate()), 23),1,4)
if @PSTOPTIME is null ---设备停机总时间
   SET  @PSTOPTIME = 0;
if @PSTOPCPUNT  is null ---如果停机次数不存在
   SET @PSTOPCPUNT = 1 ;
---设备运行总时间
--SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))-1, 0),dateadd(ms,-2,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0)));

SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
WHERE PRODUCTLINE = @PPRODUCTIONLINE
AND CONVERT(VARCHAR(4),PPDATE,20) = CONVERT(VARCHAR(4),DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))-1, 0),20)

if @PREALTIME is null
    SET @PREALTIME = 0 ;
---总时间
SET @PPLANTIME  = dbo.USF_GET_PLANTIME(DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))-1, 0),dateadd(ms,-2,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0)));
if @PPLANTIME is null
   SET @PPLANTIME = 1;
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
select @TAG = COUNT(1) FROM PMC_PP_KPIYEAR where YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(year,-1,getdate()), 23),1,4) and PRODUCTIONLINE = @PPRODUCTIONLINE 
if @TAG = 0
 begin
INSERT  into PMC_PP_KPIYEAR(YYYY,PRODUCTIONLINE,PRODUCTIONLINENAME,MTTR,MTBF,EQMRATE,EQPSTOP,STOPTIME,STOPCOUNT,OPERUSER)values
 (SUBSTRING(CONVERT(varchar(100), dateadd(year,-1,getdate()), 23),1,4),@PPRODUCTIONLINE,@PPRODUCTIONLINENAME,@PMTTR,@PMTBF,@VARCHARPEQMRATE,@VARCHARPEQPSTOP,@PSTOPTIME,@PSTOPCPUNT,@SEQ)
 end
 else
 begin
 update PMC_PP_KPIYEAR set MTTR = @PMTTR ,MTBF = @PMTBF ,EQMRATE = @VARCHARPEQMRATE,EQPSTOP = @VARCHARPEQPSTOP,STOPTIME = @PSTOPTIME,STOPCOUNT=@PSTOPCPUNT,UPDATETIME=getdate() where YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(year,-1,getdate()), 23),1,4) and PRODUCTIONLINE = @PPRODUCTIONLINE 
 end


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