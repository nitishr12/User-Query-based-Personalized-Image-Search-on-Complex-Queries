# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

import os
import sys
import time
from urllib import FancyURLopener
import urllib2
import json as simplejson
import itertools
import Tkinter
from Tkinter import *
#from PIL import ImageTk, Image
# Define search term
# Start FancyURLopener with defined version 
#top = Tkinter.Tk()
#sq= Label(top, text="Downloading")
#sq.pack(side=LEFT)
#e1=Entry(top,bd=5)
#e1.pack(side = LEFT)
#def srcq():    
class MyOpener(FancyURLopener): 
    version = 'Mozilla/5.0 (Windows; U; Windows NT 5.1; it; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11'
myopener = MyOpener()

#can = Canvas(top, width=160, height=160, bg='white'    
#def execquery():
with open ("data.txt", "r") as myfile:
    data=myfile.read()
searchTerm = data

# Replace spaces ' ' in search term for '%20' in order to comply with request
searchTerm = searchTerm.replace(' ','%20')

# Set count to 0
count= 0
it=0
for i in range(0,10):

    # Notice that the start changes for each iteration in order to request a new set of images for each loop
    if it in (0,1):
        url = ('https://ajax.googleapis.com/ajax/services/search/images?' + 'v=1.0&q='+searchTerm+'&start='+str(i*4)+'&userip=MyIP')
        print url
        request = urllib2.Request(url, None, {'Referer': 'testing'})
        response = urllib2.urlopen(request)

        # Get results using JSON
        results = simplejson.load(response)
        data = results['responseData']
        dataInfo = data['results']
        it=it+1

            # Iterate for each result and get unescaped url
        for myUrl in dataInfo:
            count = count + 1
            print myUrl['unescapedUrl']

            myopener.retrieve(myUrl['unescapedUrl'],str(count)+'.jpg')

        # Sleep for one second to prevent IP blocking from Google
        time.sleep(1)
    #return searchTerm    
#B = Tkinter.Button(top, text ="Search", command = srcq)
#B.pack()
#def disp():
    #img = ImageTk.PhotoImage(Image.open("1.jpg"))
    #panel = Label(root, image = img)
    #panel.pack(side = "bottom", fill = "both", expand = "yes")
    #top.destroy()
    #time.sleep(1) 
#B1 = Tkinter.Button(top, text ="Display", command = disp)
#B1.pack()
#top.mainloop()

    

