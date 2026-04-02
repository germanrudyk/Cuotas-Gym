import requests
import time 
import mysql.connector
from datetime import datetime
from datetime import date
import pyautogui, webbrowser
from time import sleep
cnx = mysql.connector.connect(
    user='root',
    password='root',
    host='localhost',
    database='cuotasgym'
)

cursor = cnx.cursor()

# Obtener todas las rutinas y el numero de telefono de cada cliente
cursor.execute("SELECT c.telefono, r.detalle FROM cliente c inner JOIN rutina r ON c.id = r.cliente_id WHERE r.id = 'b4535d98-1a60-42ac-996d-f8e292a66cc4';")

rows = cursor.fetchall()



for row in rows:

        # Enviar mensaje
        webbrowser.open('https://web.whatsapp.com/send?phone='+row[0])
        sleep(15)
        pyautogui.typewrite(row[1])
        pyautogui.press('enter')


cursor.close()
cnx.close()