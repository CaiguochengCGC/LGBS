USE [WebReport_DB]
GO
/****** Object:  StoredProcedure [dbo].[USP_GET_PMC_EQUIPMENTSTOP]    Script Date: 08/24/2015 09:47:12 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER  PROCEDURE  [dbo].[USP_GET_PMC_EQUIPMENTSTOP] 
  @ERROR_TAG   CHAR(1)        OUTPUT,      --�����־��0.�ɹ�,1.����
  @ERROR_MSG   NVARCHAR(4000) OUTPUT       --������Ϣ
As 
Begin
DECLARE  @PSTATION   varchar(1024) ;
DECLARE  @FPSTATION   varchar(1024) ;---����
DECLARE  @FPSTATIONNAME   varchar(1024) ;---����mingzi
DECLARE  @SEQ	int;--�������
DECLARE  @PSTOPTIME  int;
DECLARE  @PSTOPCOUNT int;
DECLARE  @PSTOPTIMEM int;
DECLARE  @PDATA8  int;----
DECLARE  @PDATA9 int;
DECLARE  @PDATA10 int;
DECLARE  @PDATA11  int;
DECLARE  @PDATA12 int;
DECLARE  @PDATA13 int;
DECLARE  @PDATA14  int;
DECLARE  @PDATA15 int;
DECLARE  @PDATA16 int;
DECLARE  @PDATA17  int;
DECLARE  @PDATA18 int;
DECLARE  @PDATA19 int;
DECLARE  @PDATA20 int;
DECLARE  @PDATA21  int;
DECLARE  @PDATA22 int;
DECLARE  @PDATA23 int;
DECLARE  @PDATA24  int;
DECLARE  @PDATA25 int;
DECLARE  @PDATA26 int;
DECLARE  @PDATA27  int;
DECLARE  @PDATA28 int;
DECLARE  @PDATA29 int;
DECLARE  @PDATA30 int;
DECLARE  @PDATA31  int;
DECLARE  @PDATA32 int;
DECLARE  @PDATA33 int;
DECLARE  @PDATA34  int;
DECLARE  @PDATA35 int;
DECLARE  @PDATA36 int;
DECLARE  @PDATA37  int;
DECLARE  @PDATA38 int;
DECLARE  @PDATA39 int;
DECLARE  @EQUMENT   varchar(1024) ;---�豸
DECLARE  @COUNTNUM int;----�豸��������

BEGIN TRY
    BEGIN TRANSACTION

DECLARE  cur_data  cursor  for
--Select  EventData4  ,EventData7 ,
--SUM(eventData1)  as  PSTOPTIME , 
--count(EventData4)  
--from  [dbo].[tabStopSta] 

select eventdate1,eventdate40,eventdate42, 
sum(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))),
COUNT(EventDate8) ,EventDate43
from PMC_STOP_STA
where CONVERT(varchar(100), EventDataG, 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and 
CONVERT(varchar(100), EventDataG, 20) <= CONVERT(varchar(100), getdate(), 20)
group by  eventdate1,eventdate40,eventdate42,EventDate43

open  cur_data						--��λ	  ��������		����Ӣ����	ͣ��ʱ��	ͣ�ߴ���
fetch  next  from  cur_data  into  @PSTATION,@FPSTATIONNAME,@FPSTATION,@PSTOPTIME, @PSTOPCOUNT,@SEQ
while(@@fetch_status  =  0) 
begin 
SET @PSTOPTIMEM = convert(int, @PSTOPTIME/60)----ʱ��(����);

------- EventData8������
Select @PDATA8 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate8)  from  [dbo].[PMC_STOP_STA]
where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate8!='' and EventDate8 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA8 is null 
begin
SET @PDATA8 = 0
end
else
begin
-----------xiao youbiao 
SET @PDATA8 =  convert(int, @PDATA8/60)

end
---8����

------- EventData9������
Select @PDATA9 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate9)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION and EventDate42 = @FPSTATION and EventDate9!='' and EventDate9 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA9 is null
SET @PDATA9 = 0
else
begin
-----------xiao youbiao 
SET @PDATA9 =  convert(int, @PDATA9/60)
end
---9����
------- EventData10������
Select @PDATA10 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate10)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION and EventDate42 = @FPSTATION and  EventDate10!='' and EventDate10 is not null and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA10 is null
SET @PDATA10 = 0
else
begin
-----------xiao youbiao 
SET @PDATA10 =  convert(int, @PDATA10/60)

end 
---10����
------- EventData11������
Select @PDATA11 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate11)  from  [dbo].[PMC_STOP_STA]
  where EventDate1 = @PSTATION and EventDate42 = @FPSTATION and    EventDate11!=''   and EventDate11 is not null   and  
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA11 is null
SET @PDATA11 = 0
else
begin
-----------xiao youbiao 
SET @PDATA11 =  convert(int, @PDATA11/60)

end
---11����
------- EventData12������
Select @PDATA12 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate12)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate12!='' and  EventDate12 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA12 is null
SET @PDATA12 = 0
else
begin
-----------xiao youbiao 
SET @PDATA12 =  convert(int, @PDATA12/60)

end
---12����
------- EventData13������
Select @PDATA13 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate13)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate13!=''    and  EventDate13 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA13 is null
SET @PDATA13 = 0
else
begin
-----------xiao youbiao 
SET @PDATA13 =  convert(int, @PDATA13/60)

end
---13����
------- EventData14������
Select @PDATA14 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate14)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate14!=''  and  EventDate14 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA14 is null
SET @PDATA14 = 0
else
begin
-----------xiao youbiao
SET @PDATA14 =  convert(int, @PDATA14/60) 

end
---14����
------- EventData15������
Select @PDATA15 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate15)  from  [dbo].[PMC_STOP_STA]
  where EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate15!=''   and  EventDate15 is not null and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA15 is null
SET @PDATA15= 0
else
begin
-----------xiao youbiao 
SET @PDATA15 =  convert(int, @PDATA15/60) 
end
---15����
------- EventData16������
Select @PDATA16 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate16)  from  [dbo].[PMC_STOP_STA]
  where EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate16!=''   and  EventDate16 is not null and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA16 is null
SET @PDATA16= 0
else
begin
-----------xiao youbiao 
SET @PDATA16=  convert(int, @PDATA16/60) 

end
---16����
------- EventData17������
Select @PDATA17 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate17)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate17!=''   and  EventDate17 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA17 is null
SET @PDATA17= 0
else
begin
-----------xiao youbiao 
SET @PDATA17=  convert(int, @PDATA17/60) 

end
---17����
------- EventData18������
Select @PDATA18 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate18)  from  [dbo].[PMC_STOP_STA]
  where   EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and   EventDate18!=''  and  EventDate18 is not null and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA18 is null
SET @PDATA18= 0
else
begin
-----------xiao youbiao 
SET @PDATA18=  convert(int, @PDATA18/60) 

end
---18����
------- EventData19������
Select @PDATA19 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate19)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and   EventDate19!=''  and  EventDate19 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA19 is null
SET @PDATA19= 0
else
begin
-----------xiao youbiao 
SET @PDATA19=  convert(int, @PDATA19/60) 

end
---19����


------- EventData30������
Select @PDATA30 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate30)  from  [dbo].[PMC_STOP_STA]
  where EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate30!=''  and  EventDate30 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA30 is null
SET @PDATA30= 0
else
begin
-----------xiao youbiao 
SET @PDATA30=  convert(int, @PDATA30/60) 

end 
---30����
------- EventData31������
Select @PDATA31 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate31)  from  [dbo].[PMC_STOP_STA]
  where EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and     EventDate31!=''  and  EventDate31 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA31 is null
SET @PDATA31= 0
else
begin
-----------xiao youbiao 
SET @PDATA31=  convert(int, @PDATA31/60) 

end
---31����
------- EventData32������
Select @PDATA32 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate32)  from  [dbo].[PMC_STOP_STA]
  where EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and     EventDate32!='' and  EventDate32 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA32 is null
SET @PDATA32= 0
else
begin
-----------xiao youbiao 
SET @PDATA32=  convert(int, @PDATA32/60) 

end
---32����
------- EventData33������
Select @PDATA33 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate33)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate33!=''  and  EventDate33 is not null and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA33 is null
SET @PDATA33= 0
else
begin
-----------xiao youbiao 
SET @PDATA33=  convert(int, @PDATA33/60) 

end
---33����
------- EventData34������
Select @PDATA34 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate34)  from  [dbo].[PMC_STOP_STA]
  where   EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and   EventDate34!=''  and  EventDate34 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA34 is null
SET @PDATA34= 0
else
begin
-----------xiao youbiao 
SET @PDATA34=  convert(int, @PDATA34/60)

end
---34����
------- EventData35������
Select @PDATA35 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate35)  from  [dbo].[PMC_STOP_STA]
  where   EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and   EventDate35!=''  and  EventDate35 is not null and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA35 is null
SET @PDATA35= 0
else
begin
-----------xiao youbiao 
SET @PDATA35=  convert(int, @PDATA35/60)

end
---35����
------- EventData36������
Select @PDATA36 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate36)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION and EventDate42 = @FPSTATION  and    EventDate36!='' and  EventDate36 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA36 is null
SET @PDATA36= 0
else
begin
-----------xiao youbiao 
SET @PDATA36=  convert(int, @PDATA36/60) 

end
---36����
------- EventData37������
Select @PDATA37 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate37)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate37!='' and  EventDate37 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA37 is null
SET @PDATA37= 0
else
begin
-----------xiao youbiao 
SET @PDATA37=  convert(int, @PDATA37/60)

end
---37����
------- EventData38������
Select @PDATA38 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate38)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate38!=''  and  EventDate38 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA38 is null
SET @PDATA38= 0
else
begin
-----------xiao youbiao 
SET @PDATA38=  convert(int, @PDATA38/60)

end
---38����
------- EventData39������
Select @PDATA39 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate39)  from  [dbo].[PMC_STOP_STA]
  where   EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and   EventDate39!='' and  EventDate39 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA39 is null
SET @PDATA39= 0
else
begin
-----------xiao youbiao 
SET @PDATA39=  convert(int, @PDATA39/60)

end
---39����

------- EventData20������
Select @PDATA20 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate20)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate20!='' and  EventDate20 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA20 is null
SET @PDATA20= 0
else
begin
-----------xiao youbiao 
SET @PDATA20=  convert(int, @PDATA20/60)

end
---20����
------- EventData21������
Select @PDATA21 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate21)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and   EventDate21!=''  and  EventDate21 is not null and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA21 is null
SET @PDATA21= 0
else
begin
-----------xiao youbiao 
SET @PDATA21=  convert(int, @PDATA21/60)

end
---21����
------- EventData22������
Select @PDATA22 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate22)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate22!='' and  EventDate22 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA22 is null
SET @PDATA22= 0
else
begin
-----------xiao youbiao 
SET @PDATA22=  convert(int, @PDATA22/60) 

end
---22����
------- EventData23������
Select @PDATA23 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate20)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and   EventDate23!='' and  EventDate23 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA23 is null
SET @PDATA23= 0
else
begin
-----------xiao youbiao 
SET @PDATA23=  convert(int, @PDATA23/60)

end
---23����
------- EventData24������
Select @PDATA24 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate24)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate24!=''  and  EventDate24 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA24 is null
SET @PDATA24= 0
else
begin
-----------xiao youbiao 
SET @PDATA24=  convert(int, @PDATA24/60) 

end
---24����
------- EventData25������
Select @PDATA25 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate25)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate25!='' and  EventDate25 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA25 is null
SET @PDATA25= 0
else
begin
-----------xiao youbiao 
SET @PDATA25=  convert(int, @PDATA25/60)

end
---25����
------- EventData26������
Select @PDATA26 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate26)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate26!='' and  EventDate26 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA26 is null
SET @PDATA26= 0
else
begin
-----------xiao youbiao 
SET @PDATA26=  convert(int, @PDATA26/60)

end
---26����
------- EventData27������
Select @PDATA27 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate27)  from  [dbo].[PMC_STOP_STA]
  where EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and     EventDate27!='' and  EventDate27 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA27 is null
SET @PDATA27= 0
else
begin
-----------xiao youbiao 
SET @PDATA27=  convert(int, @PDATA27/60)

end
---27����
------- EventData28������
Select @PDATA28 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate28)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and    EventDate28!='' and  EventDate28 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA28 is null
SET @PDATA28= 0
else
begin
-----------xiao youbiao 
SET @PDATA28=  convert(int, @PDATA28/60)

end
---28����
------- EventData29������
Select @PDATA29 = SUM(datediff(ss, convert(datetime,EventDataG), convert(datetime, EventDataR))) , @COUNTNUM =COUNT(EventDate29)  from  [dbo].[PMC_STOP_STA]
  where  EventDate1 = @PSTATION  and EventDate42 = @FPSTATION and   EventDate29!=''  and  EventDate29 is not null  and 
CONVERT(varchar(100), convert(datetime, EventDataG), 20) >= CONVERT(varchar(100), dateadd(day,-1,getdate()), 20)  and CONVERT(varchar(100), convert(datetime, EventDataG), 20) <= CONVERT(varchar(100), getdate(), 20)
if @PDATA29 is null
SET @PDATA29= 0
else
begin
-----------xiao youbiao 
SET @PDATA29=  convert(int, @PDATA29/60)

end
---29����

----���뵽PMC_EQUIPMENT_STOP������
Insert into  dbo.PMC_EQUIPMENT_STOP (PPDATE,PRODUCTIONLINE,STATION,STOPTIME,STOPCOUNT,DATA8,DATA9,DATA10,DATA11,DATA12,DATA13,DATA14,DATA15,DATA16,DATA17,DATA18,DATA19,
DATA20,DATA21,DATA22,DATA23,DATA24,DATA25,DATA26,DATA27,DATA28,DATA29,
DATA30,DATA31,DATA32,DATA33,DATA34,DATA35,DATA36,DATA37,DATA38,DATA39,PRODUCTIONLINENAME,seq)  values (dateadd(day,-1,getdate()),@FPSTATION, @PSTATION ,@PSTOPTIMEM, @PSTOPCOUNT,@PDATA8,@PDATA9,
@PDATA10,@PDATA11,@PDATA12,@PDATA13,@PDATA14,@PDATA15,@PDATA16,@PDATA17,@PDATA18,@PDATA19,
@PDATA20,@PDATA21,@PDATA22,@PDATA23,@PDATA24,@PDATA25,@PDATA26,@PDATA27,@PDATA28,@PDATA29,
@PDATA30,@PDATA31,@PDATA32,@PDATA33,@PDATA34,@PDATA35,@PDATA36,@PDATA37,@PDATA38,@PDATA39,@FPSTATIONNAME,@SEQ )

fetch  next  from  cur_data  into  @PSTATION,@FPSTATIONNAME,@FPSTATION,@PSTOPTIME, @PSTOPCOUNT,@SEQ
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