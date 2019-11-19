USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_DEL_HISTROY_DATA]    Script Date: 08/24/2015 09:46:07 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--删除历史数据存储过程 
ALTER  PROCEDURE  [dbo].[USP_DEL_HISTROY_DATA] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --错误标志：0.成功,1.错误
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --错误信息
As 
Begin
---
delete from [dbo].[tabCycleTime]  where     CONVERT(varchar(100), EventDate, 23) <= CONVERT(varchar(100), dateadd(day,-60,getdate()), 23) ;
--
--delete from [dbo].[tabProductHour] where CONVERT(varchar(100), convert(datetime, EventData), 23) <= CONVERT(varchar(100), dateadd(day,-400,getdate()), 23) ;
--
delete from  [dbo].[tabStopLine] where CONVERT(varchar(100), convert(datetime, EventDataG), 23) <= CONVERT(varchar(100), dateadd(day,-400,getdate()), 23) ;
--
--delete from  [dbo].[tabStoplineCount] where CONVERT(varchar(100), convert(datetime, EventDate), 23) <= CONVERT(varchar(100), dateadd(day,-400,getdate()), 23) ;
--

--
delete from  [dbo].[tabStopSta] where CONVERT(varchar(100), EventDate, 23) <= CONVERT(varchar(100), dateadd(day,-400,getdate()), 23) ;


BEGIN TRY
    BEGIN TRANSACTION

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