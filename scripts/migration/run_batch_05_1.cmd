   @echo off&SETLOCAL

   :: This will return date into environment vars
   :: Works on any NT/2K/XP machine independent of regional date settings
   :: 20 March 2002

   FOR /f "tokens=1-4 delims=/-. " %%G IN ('date /t') DO (call :s_fixdate %%G %%H %%I %%J)
   goto :s_print_the_date
   
   :s_fixdate
   if "%1:~0,1%" GTR "9" shift
   FOR /f "skip=1 tokens=2-4 delims=(-)" %%G IN ('echo.^|date') DO (
       set %%G=%1&set %%H=%2&set %%I=%3)
   goto :eof

   :s_print_the_date

  SETLOCAL
  For /f "tokens=1-3 delims=1234567890 " %%a in ("%time%") Do set "delims=%%a%%b%%c"
  For /f "tokens=1-4 delims=%delims%" %%G in ("%time%") Do (
    Set _hh=%%G
    Set _min=%%H
    Set _ss=%%I
    Set _ms=%%J
  )
  :: Strip any leading spaces
  Set _hh=%_hh: =%

  :: Ensure the hours have a leading zero
  if 1%_hh% LSS 20 Set _hh=0%_hh%

  ENDLOCAL&SET startTime=%mm%/%dd%/%yy% %_hh%:%_min%:%_ss%

sqlplus.exe /nolog @00_run_Batch_05_1.sql

   @echo off&SETLOCAL

   :: This will return date into environment vars
   :: Works on any NT/2K/XP machine independent of regional date settings
   :: 20 March 2002

   FOR /f "tokens=1-4 delims=/-. " %%G IN ('date /t') DO (call :s_fixdate2 %%G %%H %%I %%J)
   goto :s_print_the_date2
   
   :s_fixdate2
   if "%1:~0,1%" GTR "9" shift
   FOR /f "skip=1 tokens=2-4 delims=(-)" %%G IN ('echo.^|date') DO (
       set %%G=%1&set %%H=%2&set %%I=%3)
   goto :eof

   :s_print_the_date2


  SETLOCAL
  For /f "tokens=1-3 delims=1234567890 " %%a in ("%time%") Do set "delims=%%a%%b%%c"
  For /f "tokens=1-4 delims=%delims%" %%G in ("%time%") Do (
    Set _hh=%%G
    Set _min=%%H
    Set _ss=%%I
    Set _ms=%%J
  )
  :: Strip any leading spaces
  Set _hh=%_hh: =%

  :: Ensure the hours have a leading zero
  if 1%_hh% LSS 20 Set _hh=0%_hh%

  ENDLOCAL&SET endTime=%mm%/%dd%/%yy% %_hh%:%_min%:%_ss%

echo Start: %startTime%
echo End  : %endTime% 