USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_CT]    Script Date: 08/24/2015 09:47:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER  PROCEDURE  [dbo].[USP_GET_PMC_CT] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --�����־��0.�ɹ�,1.����
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --������Ϣ
As  
Begin
DECLARE	@PSTATION		varchar(1024);--����
DECLARE	@PSTATIONNAME	varchar(1024);--��������
DECLARE	@FPSTATION		varchar(1024);---�������
DECLARE	@STATION		varchar(1024);--��λ
DECLARE	@PMODEL			varchar(1024);--����
DECLARE	@PDATA4			numeric(7,1);--������ʱ��
DECLARE	@PDATA5			numeric(7,1);--����ʱ��
DECLARE	@PDATA6			numeric(7,1);--��װʱ��
DECLARE	@PDATA7			numeric(7,1);--�ȴ�ʱ��
DECLARE	@PDATA8			numeric(7,1);--����ʱ��
DECLARE	@PDATA9			numeric(7,1);--�˹�ʱ��
DECLARE	@PDATA10		numeric(7,1);--ͣ��ʱ��
DECLARE	@PDATA11		numeric(7,1);--��ģ/����
DECLARE	@PDATA12		numeric(7,1);--ѹ��ʱ��
DECLARE	@PDATA14		numeric(7,1);--��λʱ��

--2015-07-17
BEGIN TRY
    BEGIN TRANSACTION

DECLARE  cur_data  cursor  for					--2015-07-17
--Select  EventData1,EventData3,MAX(EventData14),EventData15,EventData16,EventData17
--from  [dbo].[tabCycleTime] 
--where CONVERT(varchar(100), EventDate, 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and 
--CONVERT(varchar(100), EventDate, 20) <= CONVERT(varchar(100), getdate(), 20)
--group by   EventData1,EventData3,EventData15,EventData16,EventData17;

--		��λ		����	����		����������	�������
Select  EventData1,EventData3,EventData15,EventData16,EventData17
from  [dbo].[tabCycleTime] 
where CONVERT(varchar(100), EventDate, 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and 
CONVERT(varchar(100), EventDate, 20) <= CONVERT(varchar(100), getdate(), 20)
group by   EventData1,EventData3,EventData15,EventData16,EventData17;

open  cur_data			--			��λ	����	����		����������	�������
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

----���뵽PMC_STATION_CT������	--����	--ʱ��	��������	������������		�������		��λ	
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