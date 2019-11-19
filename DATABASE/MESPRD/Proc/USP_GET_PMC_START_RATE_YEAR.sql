USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_START_RATE_YEAR]    Script Date: 08/24/2015 09:50:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

---ÿ�µ�1�ŵ�8��14ִ����һ���µ�
-----�������걨��洢����     
ALTER  PROCEDURE  [dbo].[USP_GET_PMC_START_RATE_YEAR] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --�����־��0.�ɹ�,1.����
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --������Ϣ
As 
Begin
DECLARE  @PPRODUCTIONLINE  varchar(1024) ;--����
DECLARE  @PPRODUCTIONLINENAME   varchar(1024) ;---�������� 
DECLARE  @SEQ	int;--�������
DECLARE  @PSTOPTIME  int;---ͣ����ʱ��
DECLARE  @PREALTIME  int;---������ʱ��
DECLARE  @PSTOPTIMETOTL  int;---����ͣ����ʱ��
DECLARE  @PMONTHPLANCOUNT  int;
DECLARE  @PMONTHREALCOUNT  int;
DECLARE  @PMONTHRATE  numeric(6,2) ;--���¿�����
DECLARE  @PDATA  numeric(6,2);----
DECLARE  @PDATA2 varchar(10);
DECLARE  @PDATA3  varchar(10);----
DECLARE  @PDATA4 varchar(10);
DECLARE  @PDATA5 varchar(10);
DECLARE  @PDATA6  varchar(10);
DECLARE  @PDATA7 varchar(10);
DECLARE  @PDATA8  varchar(10);
DECLARE  @PDATA9 varchar(10);
DECLARE  @PDATA10 varchar(10);
DECLARE  @PDATA11  varchar(10);----
DECLARE  @DDDDDTTTT varchar(100);

DECLARE  @tag int;
BEGIN TRY
    BEGIN TRANSACTION

DECLARE  cur_data  cursor  for
Select  distinct PRODUCTIONLINE,PRODUCTIONLINENAME,PSEQ from PMC_PP_STATION   

---SELECT DISTINCT PRODUCTIONLINE,PRODUCTIONLINENAME FROM  PMC_EQUIPMENT_STOPLINE
--WHERE SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 

open  cur_data
fetch  next  from  cur_data  into  @PPRODUCTIONLINE ,@PPRODUCTIONLINENAME ,@SEQ
while(@@fetch_status  =  0) 
begin 
 select @tag = COUNT(1) from dbo.PMC_PP_START_RATE_Y where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME  and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)
 if @tag = 0 
 begin
   insert into PMC_PP_START_RATE_Y (YYYY,PRODUCTIONLINE,PRODUCTIONLINENAME)values(SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4),@PPRODUCTIONLINE,@PPRODUCTIONLINENAME)
 end
  
if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='01'
begin
   SET @DDDDDTTTT = '0' ;
  SET @PREALTIME = 0 ;
  --SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0),dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0)));
  
  SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
  WHERE PRODUCTLINE = @PPRODUCTIONLINE
  AND CONVERT(DATE,PPDATE,20) >= CONVERT(DATE,DATEADD(mm, DATEDIFF(mm,0,dateadd(mm,-1,getdate())), 0),20)
  AND CONVERT(DATE,PPDATE,20) <= CONVERT(DATE,dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,dateadd(mm,-1,getdate()))+1, 0)),20)
  
  select @PSTOPTIME = SUM(ISNULL(STOPTIME,0))/60 from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
 
 if @PREALTIME is not null and @PREALTIME !=0  and @PSTOPTIME is not null 
  begin 
  SET @PDATA =(@PREALTIME-@PSTOPTIME)/(@PREALTIME+0.0)*100;
  SET @DDDDDTTTT =CAST(@PDATA as varchar);
  end 
    if @PSTOPTIME is  null and @PREALTIME is not null and   @PREALTIME !=0
  begin
     SET @DDDDDTTTT ='100';
  end 
  update dbo.PMC_PP_START_RATE_Y set DATA1 = @DDDDDTTTT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='02'
begin
   SET @DDDDDTTTT = '0' ;
  SET @PREALTIME = 0 ;
  --SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0),dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0)));
  
  SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
  WHERE PRODUCTLINE = @PPRODUCTIONLINE
  AND CONVERT(DATE,PPDATE,20) >= CONVERT(DATE,DATEADD(mm, DATEDIFF(mm,0,dateadd(mm,-1,getdate())), 0),20)
  AND CONVERT(DATE,PPDATE,20) <= CONVERT(DATE,dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,dateadd(mm,-1,getdate()))+1, 0)),20)
  
  select @PSTOPTIME = SUM(ISNULL(STOPTIME,0))/60 from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
 if @PREALTIME is not null and @PREALTIME !=0  and @PSTOPTIME is not null 
  begin 
  SET @PDATA =(@PREALTIME-@PSTOPTIME)/(@PREALTIME+0.0)*100;
  SET @DDDDDTTTT =CAST(@PDATA as varchar);
  end 
    if @PSTOPTIME is  null and @PREALTIME is not null and   @PREALTIME !=0
  begin
     SET @DDDDDTTTT ='100';
  end 
  
  update dbo.PMC_PP_START_RATE_Y set DATA2 = @DDDDDTTTT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='03'
begin
    SET @DDDDDTTTT = '0' ;
  SET @PREALTIME = 0 ;
  --SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0),dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0)));
  
  SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
  WHERE PRODUCTLINE = @PPRODUCTIONLINE
  AND CONVERT(DATE,PPDATE,20) >= CONVERT(DATE,DATEADD(mm, DATEDIFF(mm,0,dateadd(mm,-1,getdate())), 0),20)
  AND CONVERT(DATE,PPDATE,20) <= CONVERT(DATE,dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,dateadd(mm,-1,getdate()))+1, 0)),20)
  
  select @PSTOPTIME = SUM(ISNULL(STOPTIME,0))/60 from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
 if @PREALTIME is not null and @PREALTIME !=0  and @PSTOPTIME is not null 
  begin 
  SET @PDATA =(@PREALTIME-@PSTOPTIME)/(@PREALTIME+0.0)*100;
  SET @DDDDDTTTT =CAST(@PDATA as varchar);
  end 
    if @PSTOPTIME is  null and @PREALTIME is not null and   @PREALTIME !=0
  begin
     SET @DDDDDTTTT ='100';
  end 
  update dbo.PMC_PP_START_RATE_Y set DATA3 = @DDDDDTTTT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='04'
begin
   SET @DDDDDTTTT = '0' ;
  SET @PREALTIME = 0 ;
  --SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0),dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0)));
  
  SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
  WHERE PRODUCTLINE = @PPRODUCTIONLINE
  AND CONVERT(DATE,PPDATE,20) >= CONVERT(DATE,DATEADD(mm, DATEDIFF(mm,0,dateadd(mm,-1,getdate())), 0),20)
  AND CONVERT(DATE,PPDATE,20) <= CONVERT(DATE,dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,dateadd(mm,-1,getdate()))+1, 0)),20)
  
  select @PSTOPTIME = SUM(ISNULL(STOPTIME,0))/60 from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
 if @PREALTIME is not null and @PREALTIME !=0  and @PSTOPTIME is not null 
  begin 
  SET @PDATA =(@PREALTIME-@PSTOPTIME)/(@PREALTIME+0.0)*100;
  SET @DDDDDTTTT =CAST(@PDATA as varchar);
  end 
  if @PSTOPTIME is  null and @PREALTIME is not null and   @PREALTIME !=0
  begin
     SET @DDDDDTTTT ='100';
  end 
  update dbo.PMC_PP_START_RATE_Y set DATA4 = @DDDDDTTTT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='05'
begin
   --SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0),dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0)));
  
  SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
  WHERE PRODUCTLINE = @PPRODUCTIONLINE
  AND CONVERT(DATE,PPDATE,20) >= CONVERT(DATE,DATEADD(mm, DATEDIFF(mm,0,dateadd(mm,-1,getdate())), 0),20)
  AND CONVERT(DATE,PPDATE,20) <= CONVERT(DATE,dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,dateadd(mm,-1,getdate()))+1, 0)),20)
  
  select @PSTOPTIME = SUM(ISNULL(STOPTIME,0))/60 from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
  if @PREALTIME is not null and @PREALTIME !=0  and @PSTOPTIME is not null 
  begin 
  SET @PDATA =(@PREALTIME-@PSTOPTIME)/(@PREALTIME+0.0)*100;
  SET @DDDDDTTTT =CAST(@PDATA as varchar);
  end 
  if @PSTOPTIME is  null and @PREALTIME is not null and   @PREALTIME !=0
  begin
     SET @DDDDDTTTT ='100';
  end 
  
  update dbo.PMC_PP_START_RATE_Y set DATA5 = @DDDDDTTTT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='06'
begin
   --SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0),dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0)));
  
  SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
  WHERE PRODUCTLINE = @PPRODUCTIONLINE
  AND CONVERT(DATE,PPDATE,20) >= CONVERT(DATE,DATEADD(mm, DATEDIFF(mm,0,dateadd(mm,-1,getdate())), 0),20)
  AND CONVERT(DATE,PPDATE,20) <= CONVERT(DATE,dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,dateadd(mm,-1,getdate()))+1, 0)),20)
  
  select @PSTOPTIME = SUM(ISNULL(STOPTIME,0))/60 from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
  if @PREALTIME is not null and @PREALTIME !=0  and @PSTOPTIME is not null 
  begin 
  SET @PDATA =(@PREALTIME-@PSTOPTIME)/(@PREALTIME+0.0)*100;
  SET @DDDDDTTTT =CAST(@PDATA as varchar);
  end 
  if @PSTOPTIME is  null and @PREALTIME is not null and   @PREALTIME !=0
  begin
     SET @DDDDDTTTT ='100';
  end 
  
  update dbo.PMC_PP_START_RATE_Y set DATA6 = @DDDDDTTTT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='07'
begin
   --SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0),dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0)));
  
  SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
  WHERE PRODUCTLINE = @PPRODUCTIONLINE
  AND CONVERT(DATE,PPDATE,20) >= CONVERT(DATE,DATEADD(mm, DATEDIFF(mm,0,dateadd(mm,-1,getdate())), 0),20)
  AND CONVERT(DATE,PPDATE,20) <= CONVERT(DATE,dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,dateadd(mm,-1,getdate()))+1, 0)),20)
  
  select @PSTOPTIME = SUM(ISNULL(STOPTIME,0))/60 from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
  if @PREALTIME is not null and @PREALTIME !=0  and @PSTOPTIME is not null 
  begin 
  SET @PDATA =(@PREALTIME-@PSTOPTIME)/(@PREALTIME+0.0)*100;
  SET @DDDDDTTTT =CAST(@PDATA as varchar);
  end 
  if @PSTOPTIME is  null and @PREALTIME is not null and   @PREALTIME !=0
  begin
     SET @DDDDDTTTT ='100';
  end 
  
 update dbo.PMC_PP_START_RATE_Y set DATA7 = @DDDDDTTTT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='08'
begin
   --SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0),dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0)));
  set @PREALTIME = 0;
  set @PSTOPTIME = 0;
  SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
  WHERE PRODUCTLINE = @PPRODUCTIONLINE
  AND CONVERT(DATE,PPDATE,20) >= CONVERT(DATE,DATEADD(mm, DATEDIFF(mm,0,dateadd(mm,-1,getdate())), 0),20)
  AND CONVERT(DATE,PPDATE,20) <= CONVERT(DATE,dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,dateadd(mm,-1,getdate()))+1, 0)),20)
  
  select @PSTOPTIME = SUM(ISNULL(STOPTIME,0))/60 from PMC_EQUIPMENT_STOPLINE 
  where PRODUCTIONLINE = @PPRODUCTIONLINE 
  and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
  
  if @PREALTIME is not null and @PREALTIME !=0  and @PSTOPTIME is not null 
  begin 
  SET @PDATA =(@PREALTIME-@PSTOPTIME)/(@PREALTIME+0.0)*100;
  SET @DDDDDTTTT =CAST(@PDATA as varchar);
  end 
  if @PSTOPTIME is  null and @PREALTIME is not null and   @PREALTIME !=0
  begin
     SET @DDDDDTTTT ='100';
  end 
  
  update dbo.PMC_PP_START_RATE_Y set DATA8 = @DDDDDTTTT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='09'
begin
   --SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0),dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0)));
  
  SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
  WHERE PRODUCTLINE = @PPRODUCTIONLINE
  AND CONVERT(DATE,PPDATE,20) >= CONVERT(DATE,DATEADD(mm, DATEDIFF(mm,0,dateadd(mm,-1,getdate())), 0),20)
  AND CONVERT(DATE,PPDATE,20) <= CONVERT(DATE,dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,dateadd(mm,-1,getdate()))+1, 0)),20)
  
  select @PSTOPTIME = SUM(ISNULL(STOPTIME,0))/60 from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
  if @PREALTIME is not null and @PREALTIME !=0  and @PSTOPTIME is not null 
  begin 
  SET @PDATA =(@PREALTIME-@PSTOPTIME)/(@PREALTIME+0.0)*100;
  SET @DDDDDTTTT =CAST(@PDATA as varchar);
  end 
  if @PSTOPTIME is  null and @PREALTIME is not null and   @PREALTIME !=0
  begin
     SET @DDDDDTTTT ='100';
  end 
  
 update dbo.PMC_PP_START_RATE_Y set DATA9 = @DDDDDTTTT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='10'
begin
  --SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0),dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0)));
  
  SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
  WHERE PRODUCTLINE = @PPRODUCTIONLINE
  AND CONVERT(DATE,PPDATE,20) >= CONVERT(DATE,DATEADD(mm, DATEDIFF(mm,0,dateadd(mm,-1,getdate())), 0),20)
  AND CONVERT(DATE,PPDATE,20) <= CONVERT(DATE,dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,dateadd(mm,-1,getdate()))+1, 0)),20)
  
  select @PSTOPTIME = SUM(ISNULL(STOPTIME,0))/60 from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
  if @PREALTIME is not null and @PREALTIME !=0  and @PSTOPTIME is not null 
  begin 
  SET @PDATA =(@PREALTIME-@PSTOPTIME)/(@PREALTIME+0.0)*100;
  SET @DDDDDTTTT =CAST(@PDATA as varchar);
  end 
  if @PSTOPTIME is  null and @PREALTIME is not null and   @PREALTIME !=0
  begin
     SET @DDDDDTTTT ='100';
  end 
  
  update dbo.PMC_PP_START_RATE_Y set DATA10 = @DDDDDTTTT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='11'
begin
   --SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0),dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0)));
  
  SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
  WHERE PRODUCTLINE = @PPRODUCTIONLINE
  AND CONVERT(DATE,PPDATE,20) >= CONVERT(DATE,DATEADD(mm, DATEDIFF(mm,0,dateadd(mm,-1,getdate())), 0),20)
  AND CONVERT(DATE,PPDATE,20) <= CONVERT(DATE,dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,dateadd(mm,-1,getdate()))+1, 0)),20)
  
  select @PSTOPTIME = SUM(ISNULL(STOPTIME,0))/60 from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
  if @PREALTIME is not null and @PREALTIME !=0  and @PSTOPTIME is not null 
  begin 
  SET @PDATA =(@PREALTIME-@PSTOPTIME)/(@PREALTIME+0.0)*100;
  SET @DDDDDTTTT =CAST(@PDATA as varchar);
  end 
  if @PSTOPTIME is  null and @PREALTIME is not null and   @PREALTIME !=0
  begin
     SET @DDDDDTTTT ='100';
  end 
  
  update dbo.PMC_PP_START_RATE_Y set DATA11 = @DDDDDTTTT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4) 
end


if SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),6,2) ='12'
begin
      SET @DDDDDTTTT = '0.00%' ;
  SET @PREALTIME = 0 ;
  --SET @PREALTIME  = dbo.USF_GET_REALTIME(@PPRODUCTIONLINE,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0),dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0)));
  
  SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
  WHERE PRODUCTLINE = @PPRODUCTIONLINE
  AND CONVERT(DATE,PPDATE,20) >= CONVERT(DATE,DATEADD(mm, DATEDIFF(mm,0,dateadd(mm,-1,getdate())), 0),20)
  AND CONVERT(DATE,PPDATE,20) <= CONVERT(DATE,dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,dateadd(mm,-1,getdate()))+1, 0)),20)
  
  select @PSTOPTIME = SUM(ISNULL(STOPTIME,0))/60 from PMC_EQUIPMENT_STOPLINE where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
  SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,7)  = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,7) 
if @PREALTIME is not null and @PREALTIME !=0  and @PSTOPTIME is not null 
  begin 
  SET @PDATA =(@PREALTIME-@PSTOPTIME)/(@PREALTIME+0.0)*100;
  SET @DDDDDTTTT =CAST(@PDATA as varchar);
  end 
  if @PSTOPTIME is  null and @PREALTIME is not null and   @PREALTIME !=0
  begin
     SET @DDDDDTTTT ='100';
  end 
  
  update dbo.PMC_PP_START_RATE_Y set DATA12 = @DDDDDTTTT where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)
end
--�����������
update dbo.PMC_PP_START_RATE_Y set seq = @seq where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
   and  YYYY = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)


SET @PREALTIME = 0 ;
SET @PSTOPTIMETOTL = 0 ;
SET @PMONTHRATE = 0
--ͣ����ʱ��
select  @PSTOPTIMETOTL  = SUM(ISNULL(STOPTIME,0))/60  from PMC_EQUIPMENT_STOPLINE 
where PRODUCTIONLINE = @PPRODUCTIONLINE 
and PRODUCTIONLINENAME = @PPRODUCTIONLINENAME and 
SUBSTRING(CONVERT(varchar(100), convert(datetime, PPDATE), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)

--������ʱ��
SELECT @PREALTIME = SUM(ISNULL(ONEWORKTIME,0)+ISNULL(TWOWORKTIME,0)+ISNULL(THREEWORKTIME,0))*60 FROM PMC_PP_PRODUCT_TIME 
WHERE PRODUCTLINE = @PPRODUCTIONLINE
AND PPDATE >= DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate())), 0)
AND PPDATE <= dateadd(ms,-3,DATEADD(yy, DATEDIFF(yy,0,dateadd(day,-1,getdate()))+1, 0))

--��ȿ�����
if @PREALTIME is not  null and @PREALTIME !=0  and @PSTOPTIMETOTL is not null 
begin   
	SET @PMONTHRATE =(@PREALTIME-@PSTOPTIMETOTL)/(@PREALTIME+0.0)*100;
end

--��ʵ�ʲ���,��ѯ�������
 set @PMONTHREALCOUNT = 0 ; 

SELECT @PMONTHREALCOUNT = COUNT(EVENTDATA4) FROM tabProduct
WHERE 1=1 
and SUBSTRING(CONVERT(varchar(100), convert(datetime, EventDate), 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)
AND EVENTDATA4 = 0 AND EventData5 = @PPRODUCTIONLINE AND EventData6 = @PPRODUCTIONLINENAME
if @PMONTHREALCOUNT is null 
begin
	set @PMONTHREALCOUNT = 0
end

--�ܼƻ�����
SELECT @PMONTHPLANCOUNT = SUM(CONVERT(INT,EventDate31)) FROM tabProductHour 
where  EventDate1 = @PPRODUCTIONLINE AND EventDate30 = @PPRODUCTIONLINENAME and
SUBSTRING(CONVERT(varchar(100), EventData, 23),1,4) = SUBSTRING(CONVERT(varchar(100), dateadd(day,-1,getdate()), 23),1,4)

update dbo.PMC_PP_START_RATE_Y set YYRATE = @PMONTHRATE,YYSTOPTIME = @PSTOPTIMETOTL ,YYREALP = @PMONTHREALCOUNT,YYPLANP = @PMONTHPLANCOUNT  where PRODUCTIONLINE = @PPRODUCTIONLINE and PRODUCTIONLINENAME =@PPRODUCTIONLINENAME
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