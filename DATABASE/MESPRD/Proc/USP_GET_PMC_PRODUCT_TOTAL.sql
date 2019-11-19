USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_PRODUCT_TOTAL]    Script Date: 08/24/2015 09:49:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--产量查询
--每天早上8:02分统计数据
ALTER  PROCEDURE  [dbo].[USP_GET_PMC_PRODUCT_TOTAL] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --错误标志：0.成功,1.错误
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --错误信息
As 
Begin
DECLARE  @PSTATION   varchar(1024) ;
DECLARE  @FPSTATION   varchar(1024) ;---线体(工段)
DECLARE  @FPSTATIONNAME   varchar(1024) ;---线体mingzi(工段)
DECLARE  @IP21	int;--
DECLARE  @IP22  int;
DECLARE  @IP23	int;
DECLARE  @ZP11	int;
DECLARE  @BP31  int;----
DECLARE  @IP24	int;
DECLARE  @BP32	int;
DECLARE  @AS21  int;
DECLARE  @PRODUCTTOTAL int;--总产量
DECLARE  @SEQ INT;

BEGIN TRY
    BEGIN TRANSACTION

DECLARE  cur_data  cursor  for
--Select  distinct PRODUCTIONLINE,PRODUCTIONLINENAME from PMC_PP_STATION
SELECT PRODUCTIONLINE,PRODUCTIONLINENAME,PSEQ FROM PMC_PP_STATION_PRODUCT
--select distinct eventdata5,eventData6,EventData11 from tabProduct
--where convert(varchar(10),eventdate,20) = convert(varchar(10),getDate()-1,20)
open  cur_data						--线体英文名	线体名称
fetch  next  from  cur_data  into  @FPSTATION,@FPSTATIONNAME,@SEQ
while(@@fetch_status  =  0) 
begin 

--IP21
set @IP21 = 0;
select @IP21 = COUNT(EVENTDATA3) --,@SEQ = EventData11
from tabProduct
WHERE EVENTDATA4 = 0
AND convert(varchar(10),eventdate,20) = CONVERT(varchar(10), getdate()-1, 20)
AND EventData3 = 'IP21'
AND EVENTDATA6 = @FPSTATIONNAME
GROUP BY convert(varchar(10),eventdate,20),EventData5,EventData6,EventData11

--IP22
set @IP22 = 0;
select @IP22 = COUNT(EVENTDATA3)-- ,@SEQ = EventData11
from tabProduct
WHERE EVENTDATA4 = 0
AND convert(varchar(10),eventdate,20) = CONVERT(varchar(10), getdate()-1, 20)
AND EventData3 = 'IP22'
AND EVENTDATA6 = @FPSTATIONNAME
GROUP BY convert(varchar(10),eventdate,20),EventData5,EventData6,EventData11

--IP23
set @IP23 = 0;
select @IP23 = COUNT(EVENTDATA3)-- ,@SEQ = EventData11
from tabProduct
WHERE EVENTDATA4 = 0
AND convert(varchar(10),eventdate,20) = CONVERT(varchar(10), getdate()-1, 20)
AND EventData3 = 'IP23'
AND EVENTDATA6 = @FPSTATIONNAME
GROUP BY convert(varchar(10),eventdate,20),EventData5,EventData6,EventData11

--ZP11
set @ZP11 = 0;
select @ZP11 = COUNT(EVENTDATA3)-- ,@SEQ = EventData11
from tabProduct
WHERE EVENTDATA4 = 0
AND convert(varchar(10),eventdate,20) = CONVERT(varchar(10), getdate()-1, 20)
AND EventData3 = 'ZP11'
AND EVENTDATA6 = @FPSTATIONNAME
GROUP BY convert(varchar(10),eventdate,20),EventData5,EventData6,EventData11

--BP31
set @BP31 = 0;
select @BP31 = COUNT(EVENTDATA3)-- ,@SEQ = EventData11
from tabProduct
WHERE EVENTDATA4 = 0
AND convert(varchar(10),eventdate,20) = CONVERT(varchar(10), getdate()-1, 20)
AND EventData3 = 'BP31'
AND EVENTDATA6 = @FPSTATIONNAME
GROUP BY convert(varchar(10),eventdate,20),EventData5,EventData6,EventData11

--IP24
set @IP24 = 0;
select @IP24 = COUNT(EVENTDATA3)-- ,@SEQ = EventData11
from tabProduct
WHERE EVENTDATA4 = 0
AND convert(varchar(10),eventdate,20) = CONVERT(varchar(10), getdate()-1, 20)
AND EventData3 = 'IP24'
AND EVENTDATA6 = @FPSTATIONNAME
GROUP BY convert(varchar(10),eventdate,20),EventData5,EventData6,EventData11

--BP32
set @BP32 = 0;
select @BP32 = COUNT(EVENTDATA3)-- ,@SEQ = EventData11
from tabProduct
WHERE EVENTDATA4 = 0
AND convert(varchar(10),eventdate,20) = CONVERT(varchar(10), getdate()-1, 20)
AND EventData3 = 'BP32'
AND EVENTDATA6 = @FPSTATIONNAME
GROUP BY convert(varchar(10),eventdate,20),EventData5,EventData6,EventData11

--AS21
set @AS21 = 0;
select @AS21 = COUNT(EVENTDATA3)-- ,@SEQ = EventData11
from tabProduct
WHERE EVENTDATA4 = 0
AND convert(varchar(10),eventdate,20) = CONVERT(varchar(10), getdate()-1, 20)
AND EventData3 = 'AS21'
AND EVENTDATA6 = @FPSTATIONNAME
GROUP BY convert(varchar(10),eventdate,20),EventData5,EventData6,EventData11

--总产量
set @PRODUCTTOTAL = @IP21 + @IP22 + @IP23 + @ZP11 + @BP31 + @IP24 + @BP32 + @AS21

insert PMC_PP_PRODUCT(PPDATE,EventData5,EventData6,IP21,IP22,IP23,ZP11,BP31,IP24,BP32,AS21,productTotal,seq)
values(CONVERT(varchar(10), getdate()-1, 20),@FPSTATION,@FPSTATIONNAME,@IP21,@IP22,@IP23,@ZP11,@BP31,@IP24,@BP32,@AS21,@PRODUCTTOTAL,@SEQ)

fetch  next  from  cur_data  into  @FPSTATION,@FPSTATIONNAME,@SEQ
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