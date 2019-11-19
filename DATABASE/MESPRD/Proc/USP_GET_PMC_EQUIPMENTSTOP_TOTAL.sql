USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_EQUIPMENTSTOP_TOTAL]    Script Date: 08/24/2015 09:47:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--��λͣ��
--ÿ��5��������һ��
ALTER  PROCEDURE  [dbo].[USP_GET_PMC_EQUIPMENTSTOP_TOTAL] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --�����־��0.�ɹ�,1.����
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --������Ϣ
As 
Begin
DECLARE  @EVENTDATEG   varchar(1024) ;--��ʼʱ��
DECLARE  @EVENTDATER  varchar(1024) ;--����ʱ��
DECLARE  @EVENTDATA1  varchar(20);--��λ
DECLARE  @EVENTDATA3		varchar(20);--32��
DECLARE  @EVENTDATA2		varchar(20);--UF020
DECLARE  @EVENTDATA5		varchar(20);--�豸

DECLARE  @PDATA8	varchar(20);--32��ԭ��
DECLARE  @PDATA9	varchar(20);--32��ԭ��
DECLARE  @PDATA10	varchar(20);--32��ԭ��
DECLARE  @PDATA11	varchar(20);--32��ԭ��
DECLARE  @PDATA12	varchar(20);--32��ԭ��
DECLARE  @PDATA13	varchar(20);--32��ԭ��
DECLARE  @PDATA14	varchar(20);--32��ԭ��
DECLARE  @PDATA15	varchar(20);--32��ԭ��
DECLARE  @PDATA16	varchar(20);--32��ԭ��
DECLARE  @PDATA17	varchar(20);--32��ԭ��
DECLARE  @PDATA18	varchar(20);--32��ԭ��
DECLARE  @PDATA19	varchar(20);--32��ԭ��
DECLARE  @PDATA20	varchar(20);--32��ԭ��
DECLARE  @PDATA21	varchar(20);--32��ԭ��
DECLARE  @PDATA22	varchar(20);--32��ԭ��
DECLARE  @PDATA23	varchar(20);--32��ԭ��
DECLARE  @PDATA24	varchar(20);--32��ԭ��
DECLARE  @PDATA25	varchar(20);--32��ԭ��
DECLARE  @PDATA26	varchar(20);--32��ԭ��
DECLARE  @PDATA27	varchar(20);--32��ԭ��
DECLARE  @PDATA28	varchar(20);--32��ԭ��
DECLARE  @PDATA29	varchar(20);--32��ԭ��
DECLARE  @PDATA30	varchar(20);--32��ԭ��
DECLARE  @PDATA31	varchar(20);--32��ԭ��
DECLARE  @PDATA32	varchar(20);--32��ԭ��
DECLARE  @PDATA33	varchar(20);--32��ԭ��
DECLARE  @PDATA34	varchar(20);--32��ԭ��
DECLARE  @PDATA35	varchar(20);--32��ԭ��
DECLARE  @PDATA36	varchar(20);--32��ԭ��
DECLARE  @PDATA37	varchar(20);--32��ԭ��
DECLARE  @PDATA38	varchar(20);--32��ԭ��
DECLARE  @PDATA39	varchar(20);--32��ԭ��
DECLARE  @EVENTDATA40 varchar(20);--������������
DECLARE  @EVENTDATA41 varchar(20);--�������
DECLARE  @EVENTDATA42 varchar(20);--����(����)Ӣ����д

DECLARE	 @COUNT	int;--�жϼ�¼�Ƿ����

BEGIN TRY
    BEGIN TRANSACTION

DECLARE  cur_data  cursor  for
		--��ʼʱ��	����ʱ��
select CONVERT(VARCHAR(100),EVENTDATE,20),CONVERT(VARCHAR(100),EVENTDATE1,20),
--	32��	��λ		�������	����	����Ӣ����д	UF020		�豸
EVENTDATA3,EVENTDATA4,EVENTDATA6,EVENTDATA7,EVENTDATA8,EVENTDATA2,EVENTDATA5
from [tabStopSta] where convert(varchar(10),eventdate,23) = convert(varchar(10),getDate()-1,23)
open  cur_data						--��ʼʱ��	����ʱ��	32��		��λ		�������	����		����Ӣ����д	UF020		�豸
fetch  next  from  cur_data  into  @EVENTDATEG,@EVENTDATER,@EVENTDATA3,@EVENTDATA1,@EVENTDATA41,@EVENTDATA40,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5
while(@@fetch_status  =  0) 
begin 

--�����Ƿ���ڸü�¼
SELECT @COUNT = COUNT(*) FROM PMC_STOP_STA WHERE EVENTDATAG = @EVENTDATEG AND EVENTDATAR = @EVENTDATER

if @COUNT <= 0 
BEGIN
	if @EVENTDATA3 = '������Դ'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate8] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '���Ƶ�Դ'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate9] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'I/Oģ��'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate10] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'DNET����'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate11] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '��̫��'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate12] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '���ϴ�����'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate13] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '��ȫ�豸'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate14] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '��ͣ'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate15] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '������'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate16] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '�㺸�豸'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate17] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '������'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate18] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '��ĥ��'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate19] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '������'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate20] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'ͣ�ż�'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate21] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '��Դ'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate22] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '��ȴˮ'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate23] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '��ͷ'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate24] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '��λ��'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate25] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '����'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate26] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '����'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate27] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'ѹ��'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate28] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '�����豸'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate29] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '��װ'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate30] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'ת̨'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate31] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'ANDON'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate32] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'AVI'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate33] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '���⺸'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate34] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '�Ӿ�����'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate35] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '��̨'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate36] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '��ģ'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate37] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'CO2��'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate38] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'Ϳ���豸'
	begin						--��ʼʱ��		����ʱ��		��λ		�豸			�������	��������		����Ӣ����д	UF020		�豸
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate39] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
END

fetch  next  from  cur_data  into  @EVENTDATEG,@EVENTDATER,@EVENTDATA3,@EVENTDATA1,@EVENTDATA41,@EVENTDATA40,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5
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