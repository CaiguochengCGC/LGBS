USE [WebReport_DB]
GO
/****** Object:  UserDefinedFunction [dbo].[USF_GET_PLANCOUNT_DATA_OTHER]    Script Date: 08/24/2015 09:57:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		����Ծ
-- Create date: <Create Date, ,>
-- Description:	<Description, ,>
-- =============================================
ALTER FUNCTION [dbo].[USF_GET_PLANCOUNT_DATA_OTHER] 
(
	@WORKDATE	VARCHAR(10),
	@WORKSTART	VARCHAR(30),
	@WORKEND	VARCHAR(30),
    @PRODUCTION	VARCHAR(50),
    @PRODUCTIONNAME	VARCHAR(50),
    @TYPE	VARCHAR(20)
)
RETURNS INT
AS
BEGIN
	
	DECLARE  @PPLANCOUNR  int;
	IF @PRODUCTIONNAME != ''
		SELECT @PPLANCOUNR = CONVERT(VARCHAR(1024),COUNT(EVENTDATA5))
		FROM tabProduct
		WHERE 1=1 AND convert(varchar(10),EventDate,20) = @WORKDATE
		AND convert(varchar(50),EventDate,20) >= @WORKSTART
		AND convert(varchar(50),EventDate,20) < @WORKEND
		AND EVENTDATA5 = @PRODUCTION
		AND EventData6 = @PRODUCTIONNAME
		AND EventData8 = @TYPE
	ELSE
		SELECT @PPLANCOUNR = CONVERT(VARCHAR(1024),COUNT(EVENTDATA5))
		FROM tabProduct
		WHERE 1=1 AND convert(varchar(10),EventDate,20) = @WORKDATE
		AND convert(varchar(50),EventDate,20) >= @WORKSTART
		AND convert(varchar(50),EventDate,20) < @WORKEND
		AND EVENTDATA5 = @PRODUCTION
		AND EventData8 = @TYPE
	if @PPLANCOUNR is null
    SET @PPLANCOUNR = 0;
   
	RETURN @PPLANCOUNR

END
