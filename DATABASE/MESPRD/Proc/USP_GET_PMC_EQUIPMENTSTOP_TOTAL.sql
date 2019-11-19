USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_EQUIPMENTSTOP_TOTAL]    Script Date: 08/24/2015 09:47:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--工位停机
--每隔5分钟运行一次
ALTER  PROCEDURE  [dbo].[USP_GET_PMC_EQUIPMENTSTOP_TOTAL] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --错误标志：0.成功,1.错误
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --错误信息
As 
Begin
DECLARE  @EVENTDATEG   varchar(1024) ;--开始时间
DECLARE  @EVENTDATER  varchar(1024) ;--结束时间
DECLARE  @EVENTDATA1  varchar(20);--工位
DECLARE  @EVENTDATA3		varchar(20);--32类
DECLARE  @EVENTDATA2		varchar(20);--UF020
DECLARE  @EVENTDATA5		varchar(20);--设备

DECLARE  @PDATA8	varchar(20);--32种原因
DECLARE  @PDATA9	varchar(20);--32种原因
DECLARE  @PDATA10	varchar(20);--32种原因
DECLARE  @PDATA11	varchar(20);--32种原因
DECLARE  @PDATA12	varchar(20);--32种原因
DECLARE  @PDATA13	varchar(20);--32种原因
DECLARE  @PDATA14	varchar(20);--32种原因
DECLARE  @PDATA15	varchar(20);--32种原因
DECLARE  @PDATA16	varchar(20);--32种原因
DECLARE  @PDATA17	varchar(20);--32种原因
DECLARE  @PDATA18	varchar(20);--32种原因
DECLARE  @PDATA19	varchar(20);--32种原因
DECLARE  @PDATA20	varchar(20);--32种原因
DECLARE  @PDATA21	varchar(20);--32种原因
DECLARE  @PDATA22	varchar(20);--32种原因
DECLARE  @PDATA23	varchar(20);--32种原因
DECLARE  @PDATA24	varchar(20);--32种原因
DECLARE  @PDATA25	varchar(20);--32种原因
DECLARE  @PDATA26	varchar(20);--32种原因
DECLARE  @PDATA27	varchar(20);--32种原因
DECLARE  @PDATA28	varchar(20);--32种原因
DECLARE  @PDATA29	varchar(20);--32种原因
DECLARE  @PDATA30	varchar(20);--32种原因
DECLARE  @PDATA31	varchar(20);--32种原因
DECLARE  @PDATA32	varchar(20);--32种原因
DECLARE  @PDATA33	varchar(20);--32种原因
DECLARE  @PDATA34	varchar(20);--32种原因
DECLARE  @PDATA35	varchar(20);--32种原因
DECLARE  @PDATA36	varchar(20);--32种原因
DECLARE  @PDATA37	varchar(20);--32种原因
DECLARE  @PDATA38	varchar(20);--32种原因
DECLARE  @PDATA39	varchar(20);--32种原因
DECLARE  @EVENTDATA40 varchar(20);--线体中文名字
DECLARE  @EVENTDATA41 varchar(20);--线体归类
DECLARE  @EVENTDATA42 varchar(20);--线体(工段)英文缩写

DECLARE	 @COUNT	int;--判断记录是否存在

BEGIN TRY
    BEGIN TRANSACTION

DECLARE  cur_data  cursor  for
		--开始时间	结束时间
select CONVERT(VARCHAR(100),EVENTDATE,20),CONVERT(VARCHAR(100),EVENTDATE1,20),
--	32类	工位		线体分类	线体	线体英文缩写	UF020		设备
EVENTDATA3,EVENTDATA4,EVENTDATA6,EVENTDATA7,EVENTDATA8,EVENTDATA2,EVENTDATA5
from [tabStopSta] where convert(varchar(10),eventdate,23) = convert(varchar(10),getDate()-1,23)
open  cur_data						--开始时间	结束时间	32类		工位		线体分类	线体		线体英文缩写	UF020		设备
fetch  next  from  cur_data  into  @EVENTDATEG,@EVENTDATER,@EVENTDATA3,@EVENTDATA1,@EVENTDATA41,@EVENTDATA40,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5
while(@@fetch_status  =  0) 
begin 

--查找是否存在该记录
SELECT @COUNT = COUNT(*) FROM PMC_STOP_STA WHERE EVENTDATAG = @EVENTDATEG AND EVENTDATAR = @EVENTDATER

if @COUNT <= 0 
BEGIN
	if @EVENTDATA3 = '动力电源'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate8] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '控制电源'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate9] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'I/O模块'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate10] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'DNET网络'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate11] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '以太网'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate12] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '物料传感器'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate13] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '安全设备'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate14] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '急停'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate15] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '机器人'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate16] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '点焊设备'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate17] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '螺柱焊'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate18] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '修磨器'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate19] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '换抢盘'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate20] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '停放架'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate21] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '气源'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate22] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '冷却水'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate23] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '夹头'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate24] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '定位销'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate25] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '气缸'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate26] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '阀岛'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate27] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '压机'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate28] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '输送设备'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate29] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '工装'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate30] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '转台'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate31] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'ANDON'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate32] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'AVI'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate33] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '激光焊'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate34] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '视觉防错'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate35] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '滑台'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate36] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '修模'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate37] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = 'CO2焊'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
		INSERT INTO PMC_STOP_STA([EventDataG],[EventDataR] ,[EventDate1] ,[EventDate38] ,[EventDate41] ,[EventDate40] ,[EventDate42] ,[EventDate43] ,[EventDate44])
        VALUES (@EVENTDATEG ,@EVENTDATER ,@EVENTDATA1 ,@EVENTDATA3 ,@EVENTDATA41 ,@EVENTDATA40 ,@EVENTDATA42,@EVENTDATA2,@EVENTDATA5)
	end
	
	if @EVENTDATA3 = '涂胶设备'
	begin						--开始时间		结束时间		工位		设备			线体分类	线体名称		线体英文缩写	UF020		设备
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