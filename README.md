# TODOshka
First Android Project on Github

Монтаж

Убедитесь что в манифест файле подключено доступ блокирковки 

 <uses-permission android:name="android.permission.WAKE_LOCK"></uses-permission>
 
 Требования
 
 Версия jdk java должна быть 1.8 минимум 
 
 Баги
 
 Напоминалка в указанное время пользователя выводит Service .NotificationCompat строитель уведомления
 
 если до указанного времени вывода  уведомления перезагрузить устройство то он не сработает. Прийдется повторно
 
 вводить заново дату.Если устройство спит (это когда пользователь долго не пользовался устройством) то есть вероятность что 
 
 уведомление не сработает в указанное время пользователем.Я минимизирувал шанс  с помощью Intent Flag i PendingIntent Flag
 
 Информация
 
 Веб сайт работы с Alarm
 https://developer.android.com/reference/android/app/AlarmManager
  
  
  Installation

Make sure that blocking access is enabled in the manifest file.

Requirements

The jdk java version should be 1.8 minimum

Bugs

A reminder at a specified time of the user displays Service .NotificationCompat notification builder

If the device is restarted before the specified time for the notification, it will not work. Will come again

re-enter the date. If the device is sleeping (this is when the user has not used the device for a long time), then there is a possibility that

the notification will not work at the specified time by the user. I minimized the chance using Intent Flag i PendingIntent Flag

Information

Alarm website
 
