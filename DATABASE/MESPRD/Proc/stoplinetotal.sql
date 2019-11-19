
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE  PROCEDURE  [dbo].[stoplinetotal] 
@starttime  datetime,
@endtime    datetime,
@tag        int
As 
Begin
DECLARE  @PSTATION   varchar(1024) ;
DECLARE  @PSTOPTIME  int;
DECLARE  @PSTOPCOUNT int;
DECLARE  @PSTOPTIMEM int;

Delete  from  tabStopLineTemp  where 1=1
if (@tag=1)
BEGIN
DECLARE  cur_data  cursor  for
Select  EventDate43  , 
SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR)))  as  PSTOPTIME , 
count(EventDate43)  
from  [dbo].[tabStopLine] 
where  EventDate3 = 'A101' and   CONVERT(varchar(100), convert(datetime, EventDataG), 23) >= CONVERT(varchar(100), @starttime, 23)  and CONVERT(varchar(100), convert(datetime, EventDataG), 23) <= CONVERT(varchar(100), @endtime, 23)
group by EventDate43  ;
open  cur_data
fetch  next  from  cur_data  into  @PSTATION, @PSTOPTIME, @PSTOPCOUNT  
while(@@fetch_status  =  0) 
begin 
SET @PSTOPTIMEM = convert(int, @PSTOPTIME/60)

Insert  into  tabStopLineTemp   (STATION, STOPTIME, STOPCOUNT)values(@PSTATION, @PSTOPTIMEM, @PSTOPCOUNT)
fetch  next  from  cur_data  into  @PSTATION, @PSTOPTIME, @PSTOPCOUNT 
end
close  cur_data
DEALLOCATE  cur_data
END
else if (@tag=2)
BEGIN
DECLARE  cur_data  cursor  for
Select  EventDate1  , 
SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR)))  as  PSTOPTIME , 
count(EventDate1)  
from  [dbo].[tabStopLine] 
where  EventDate3 ='A101' and   CONVERT(varchar(100), convert(datetime, EventDataG), 23) >= CONVERT(varchar(100), @starttime, 23)  and CONVERT(varchar(100), convert(datetime, EventDataG), 23) <= CONVERT(varchar(100), @endtime, 23)
group by EventDate1  ;
open  cur_data
fetch  next  from  cur_data  into  @PSTATION, @PSTOPTIME, @PSTOPCOUNT  
while(@@fetch_status  =  0) 
begin 
SET @PSTOPTIMEM = convert(int, @PSTOPTIME/60)

Insert  into  tabStopLineTemp  (STATION, STOPTIME, STOPCOUNT)values(@PSTATION, @PSTOPTIMEM, @PSTOPCOUNT)
fetch  next  from  cur_data  into  @PSTATION, @PSTOPTIME, @PSTOPCOUNT 
end
close  cur_data
DEALLOCATE  cur_data
END

END

GO


