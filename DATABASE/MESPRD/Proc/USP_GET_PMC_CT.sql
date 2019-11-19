USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_CT]    Script Date: 08/24/2015 09:47:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER  PROCEDURE  [dbo].[USP_GET_PMC_CT] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --错误标志：0.成功,1.错误
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --错误信息
As  
Begin
DECLARE	@PSTATION		varchar(1024);--线体
DECLARE	@PSTATIONNAME	varchar(1024);--线体名称
DECLARE	@FPSTATION		varchar(1024);---线体归类
DECLARE	@STATION		varchar(1024);--工位
DECLARE	@PMODEL			varchar(1024);--车型
DECLARE	@PDATA4			numeric(7,1);--机器人时间
DECLARE	@PDATA5			numeric(7,1);--机运时间
DECLARE	@PDATA6			numeric(7,1);--工装时间
DECLARE	@PDATA7			numeric(7,1);--等待时间
DECLARE	@PDATA8			numeric(7,1);--堵料时间
DECLARE	@PDATA9			numeric(7,1);--人工时间
DECLARE	@PDATA10		numeric(7,1);--停机时间
DECLARE	@PDATA11		numeric(7,1);--换模/换门
DECLARE	@PDATA12		numeric(7,1);--压机时间
DECLARE	@PDATA14		numeric(7,1);--工位时间

--2015-07-17
BEGIN TRY
    BEGIN TRANSACTION

DECLARE  cur_data  cursor  for					--2015-07-17
--Select  EventData1,EventData3,MAX(EventData14),EventData15,EventData16,EventData17
--from  [dbo].[tabCycleTime] 
--where CONVERT(varchar(100), EventDate, 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and 
--CONVERT(varchar(100), EventDate, 20) <= CONVERT(varchar(100), getdate(), 20)
--group by   EventData1,EventData3,EventData15,EventData16,EventData17;

--		工位		车型	工段		工段中文名	线体归类
Select  EventData1,EventData3,EventData15,EventData16,EventData17
from  [dbo].[tabCycleTime] 
where CONVERT(varchar(100), EventDate, 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and 
CONVERT(varchar(100), EventDate, 20) <= CONVERT(varchar(100), getdate(), 20)
group by   EventData1,EventData3,EventData15,EventData16,EventData17;

open  cur_data			--			工位	车型	工段		工段中文名	线体归类
fetch  next  from  cur_data  into  @STATION,@PMODEL,@PSTATION,@PSTATIONNAME,@FPSTATION
while(@@fetch_status  =  0) 
begin 
Select  @PDATA4 = EventData4,@PDATA5 = EventData5,@PDATA6 = EventData6,
		@PDATA7 = EventData7,@PDATA8 = EventData8,@PDATA9 = EventData9,
		@PDATA10 = EventData10,@PDATA11 = EventData11,@PDATA12 = EventData12,
		@PDATA14 = EventData14
from  [dbo].[tabCycleTime] 
where CONVERT(varchar(100), EventDate, 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  
and CONVERT(varchar(100), EventDate, 20) <= CONVERT(varchar(100), getdate(), 20) 
and EventData1 = @STATION AND EventData3 = @PMODEL 
AND EventData15 = @PSTATION AND EventData16 = @PSTATIONNAME AND EventData17 = @FPSTATION

----插入到PMC_STATION_CT表中了	--车型	--时间	工段线体	工段线体名称		线体归类		工位	
Insert into  dbo.PMC_STATION_CT (MODEL,PPDATE,PRODUCTIONLINE,PRODUCTIONLINENAME,FPRODUCTIONLINE,STATION,DATA14,DATA4,DATA5,DATA6,DATA7,DATA8,DATA9,DATA10,DATA11,DATA12)  
values (@PMODEL,dateadd(day,-1,getdate()),@PSTATION, @PSTATIONNAME ,@FPSTATION, @STATION,@PDATA14,@PDATA4,
@PDATA5,@PDATA6,@PDATA7,@PDATA8,@PDATA9,@PDATA10,@PDATA11,@PDATA12)

fetch  next  from  cur_data  into @STATION,@PMODEL,@PSTATION,@PSTATIONNAME,@FPSTATION
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