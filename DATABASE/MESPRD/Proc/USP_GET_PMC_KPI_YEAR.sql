USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_KPI_YEAR]    Script Date: 08/24/2015 09:48:26 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-----KPI�걨��洢����  
-----ÿ��1��1������8��18������һ���kpi���������
ALTER  PROCEDURE  [dbo].[USP_GET_PMC_KPI_YEAR] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --�����־��0.�ɹ�,1.����
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --������Ϣ
As 
Begin
DECLARE  @SEQ	int;--˳��
DECLARE  @PPRODUCTIONLINE  varchar(1024) ;--����
DECLARE  @PPRODUCTIONLINENAME   varchar(1024) ;---�������� 
DECLARE  @PSTOPTIME  int;---�豸ͣ����ʱ��
DECLARE  @PREALTIME  int;---�豸������ʱ��
DECLARE  @PPLANTIME  int;---������ʱ��
DECLARE  @PSTOPCPUNT  int;---ͣ������
DECLARE  @PMTTR  int;---ƽ�������޸�ʱ��
DECLARE  @PMTBF  int;---ƽ�����Ϸ������

DECLARE  @PEQMRATE  numeric(10,2);----�豸������
DECLARE @VARCHARPEQMRATE  varchar(1024) ;
DECLARE  @PEQPSTOP  numeric(10,2);----�豸ͣ����
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
if @PSTOPTIME is null ---�豸ͣ����ʱ��
   SET  @PSTOPTIME = 0;
if @PSTOPCPUNT  is null ---���ͣ������������
   SET @PSTOPCPUNT = 1 ;
---�豸������ʱ��
--SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))-1, 0),dateadd(ms,-2,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0)));

SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
WHERE PRODUCTLINE = @PPRODUCTIONLINE
AND CONVERT(VARCHAR(4),PPDATE,20) = CONVERT(VARCHAR(4),DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))-1, 0),20)

if @PREALTIME is null
    SET @PREALTIME = 0 ;
---��ʱ��
SET @PPLANTIME  = dbo.USF_GET_PLANTIME(DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))-1, 0),dateadd(ms,-2,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0)));
if @PPLANTIME is null
   SET @PPLANTIME = 1;
---ƽ�������޸�ʱ��MTTR
 if (@PSTOPCPUNT != 0)and (@PSTOPCPUNT is not null)and(@PSTOPTIME is not null) 
 begin
 SET @PMTTR  =round(@PSTOPTIME/@PSTOPCPUNT,0);
---ƽ�����Ϸ������MTBF
 SET @PMTBF  =round(@PREALTIME/@PSTOPCPUNT,0);
 end
 if @PPLANTIME!=0 and @PPLANTIME is not null and @PREALTIME is not null and @PREALTIME !=0
 begin
 ----�豸������
 SET @PEQMRATE =@PREALTIME/(@PPLANTIME+0.0)*100 ;
 SET @VARCHARPEQMRATE =cast(@PEQMRATE as varchar) ;
 end
 ----�豸ͣ����
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