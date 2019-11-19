USE [WebReport_DB]
GO
/****** Object:  UserDefinedFunction [dbo].[USF_GET_PLANTIME]    Script Date: 08/24/2015 09:58:02 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		ÇúºéÉ­
-- Create date: <Create Date, ,>
-- Description:	<Description, ,>
-- =============================================
ALTER FUNCTION [dbo].[USF_GET_PLANTIME] 
(
	@starttime  datetime,
    @endtime    datetime
)
RETURNS INT
AS
BEGIN
	DECLARE  @PPLANTIMEMM1  int;
	DECLARE  @PPLANREST  int;
	DECLARE  @PPLANTIMEMM  int;
    select   @PPLANTIMEMM1 = SUM(datediff(mi, convert(datetime,starttime), convert(datetime, endtime))),
    @PPLANREST = SUM(RESTTiME)
	from dbo.PMC_DATE_IMPORT  where 
	CONVERT(varchar(100), convert(datetime, WORKDATE), 23) >= CONVERT(varchar(100), @starttime, 23)  and CONVERT(varchar(100), convert(datetime, WORKDATE), 23) <= CONVERT(varchar(100), @endtime, 23)
    if @PPLANTIMEMM1 is null
    SET @PPLANTIMEMM1 = 0;
    if @PPLANREST is null
    SET  @PPLANREST = 0;
	SET @PPLANTIMEMM = @PPLANTIMEMM1 - @PPLANREST 
	
	RETURN @PPLANTIMEMM

END


