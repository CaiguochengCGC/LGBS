USE [WebReport_DB]
GO
/****** Object:  Table [dbo].[tabStopLineTemp]    Script Date: 02/25/2014 23:12:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tabStopLineTemp](
    [ID] [int] IDENTITY(1,1) NOT NULL,
    [STATION] [varchar](32) NULL,
    [STOPTIME] [numeric](19, 0) NULL,
    [STOPCOUNT] [numeric](19, 0) NULL,
    [TOLTLAG] [varchar](2) NULL,
    [OPERUSER] [varchar](50) NULL,
 CONSTRAINT [PK_TABSTOPLINETEMP] PRIMARY KEY CLUSTERED 
(
    [ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[tabStopLine]    Script Date: 02/25/2014 23:12:30 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[tabStopLine](
    [Rid] [char](1) NULL,
    [EventDataG] [char](20) NOT NULL,
    [EventDataR] [char](20) NULL,
    [EventDate1] [char](20) NULL,
    [EventDate2] [char](20) NULL,
    [EventDate3] [char](20) NULL,
    [EventDate4] [char](20) NULL,
    [EventDate5] [char](20) NULL,
    [EventDate6] [char](20) NULL,
    [EventDate7] [char](20) NULL,
    [EventDate8] [char](20) NULL,
    [EventDate9] [char](20) NULL,
    [EventDate10] [char](20) NULL,
    [EventDate11] [char](20) NULL,
    [EventDate12] [char](20) NULL,
    [EventDate13] [char](20) NULL,
    [EventDate14] [char](20) NULL,
    [EventDate15] [char](20) NULL,
    [EventDate16] [char](20) NULL,
    [EventDate17] [char](20) NULL,
    [EventDate18] [char](20) NULL,
    [EventDate19] [char](20) NULL,
    [EventDate20] [char](20) NULL,
    [EventDate21] [char](20) NULL,
    [EventDate22] [char](20) NULL,
    [EventDate23] [char](20) NULL,
    [EventDate24] [char](20) NULL,
    [EventDate25] [char](20) NULL,
    [EventDate26] [char](20) NULL,
    [EventDate27] [char](20) NULL,
    [EventDate28] [char](20) NULL,
    [EventDate29] [char](20) NULL,
    [EventDate30] [char](20) NULL,
    [EventDate31] [char](20) NULL,
    [EventDate32] [char](20) NULL,
    [EventDate33] [char](20) NULL,
    [EventDate34] [char](20) NULL,
    [EventDate35] [char](20) NULL,
    [EventDate36] [char](20) NULL,
    [EventDate37] [char](20) NULL,
    [EventDate38] [char](20) NULL,
    [EventDate39] [char](20) NULL,
    [EventDate40] [char](20) NULL,
    [EventDate41] [char](20) NULL,
    [EventDate42] [char](20) NULL,
    [EventDate43] [char](20) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
