USE [WebReport_DB]
GO
/****** Object:  UserDefinedFunction [dbo].[USF_GET_PLANCOUNT_UPDATE]    Script Date: 08/24/2015 09:57:47 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		’≈“Î‘æ
-- Create date: <Create Date, ,>
-- Description:	<Description, ,>
-- =============================================
ALTER FUNCTION [dbo].[USF_GET_PLANCOUNT_UPDATE] 
(
    @WORKDATE VARCHAR(10)
)
RETURNS INT
AS
BEGIN
	
	DECLARE  @PPLANCOUNR  int;
	
    SELECT @PPLANCOUNR = SUM(PRODUCTTOTAL) FROM PMC_DATE_IMPORT WHERE CONVERT(VARCHAR(10),WORKDATE,23) = @WORKDATE
	if @PPLANCOUNR is null
    SET @PPLANCOUNR = 0;
   
	RETURN @PPLANCOUNR

END


