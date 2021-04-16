import re
import time
import requests
import chardet
import json
from bs4 import BeautifulSoup
from html.parser import HTMLParser
from html.entities import name2codepoint

class WeatherCrawl (object):
    # 1、找出https://mat1.gtimg.com/pingjs/ext2020/weather/2017/scripts/main-b0d370c158.js 获取js文件夹找出有key的url
    # 2、
    def __init__(self):
        self.province = ''
        self.city = ''
        self.mainUrl = ''
        self.apiKeyUrl = ''
        self.todayInfo = {}
        self.sevenToday = {}

    def getWeather(self):
        self.findMainUrl()
        self.findApiKeyUrl()
        self.getAddressInfo()
        self.getTemperature()
        self.getPMInfo()

    def findMainUrl(self):
        weather_url = 'https://tianqi.qq.com/'
        resp = self.getResponse(weather_url)
        if resp != None:
            soup = BeautifulSoup(resp.text, 'html.parser')
            nthchildtag = soup.select('script')
            for item in nthchildtag :
                 src = item.get('src')
                 if src != None and 'weather' in src :
                     self.mainUrl = 'https:' + src
                     print(self.mainUrl)
        print('find main url works done!')

    def findApiKeyUrl(self):
        if (self.mainUrl == ''):
            print("main url is none")
            return
        resp = self.getResponse(self.mainUrl)
        if resp != None:
            match = re.findall(r'url:\"(.+?)=jsonp', resp.text)
            if len(match) > 0:
                self.apiKeyUrl = f'http:{match[0]}=jsonp&callback=&_={self.getMilsTime()}'
            print("apiurl =", self.apiKeyUrl)

    def getAddressInfo(self):
        if (self.apiKeyUrl == ''):
            print("apiKey url is none")
            return
        resp = self.getResponse(self.apiKeyUrl)
        if resp != None:
            match = re.findall(r'QQmap&&QQmap\((.+?)\)', resp.text, re.S)
            if len(match)>0:
                data = json.loads(match[0])
                adinfo = data['result']['ad_info']
                self.province = adinfo['province']
                self.city = adinfo['city']
            print("省：", self.province)
            print("城市：", self.city)

    def getTemperature(self):
        if self.province == '':
            print("province is none")
            return
        if self.city == '':
            print("city is none")
            return
        tempurl = f'https://wis.qq.com/weather/common?source=pc&weather_type=observe%7Cforecast_1h%7Cforecast_24h%7Cindex%7Calarm%7Climit%7Ctips%7Crise&province={self.province}&city={self.city}&county=&callback=&_={self.getMilsTime()}'
        resp = self.getResponse(tempurl)
        if resp != None:
            tmpinfo = json.loads(resp.text)
            self.todayInfo["0"] = tmpinfo["data"]["forecast_1h"]
            self.todayInfo["city"] = self.city
            self.todayInfo["province"] = self.province
            for x in range(1,9):
                self.todayInfo["%s"%x] = tmpinfo["data"]["forecast_24h"]["%s"%(x-1)]
            #print('temp = ',self.todayInfo )

    def getPMInfo(self):
        if self.province == '':
            print("province is none")
            return
        if self.city == '':
            print("city is none")
            return
        pmurl = f'https://wis.qq.com/weather/common?source=pc&weather_type=air%7Crise&province={self.province}&city={self.city}&county=&callback=&_={self.getMilsTime()}'
        resp = self.getResponse(pmurl)
        if resp != None:
            pminfo = json.loads(resp.text)
            self.todayInfo["air"] = pminfo["data"]["air"]
            #self.todayInfo = {**self.todayInfo , **air}
            #print('\n\n todayInfo =',self.todayInfo)

    def getResponse(self, url):
        header = {'user-agent':'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.190 Safari/537.36'}
        with requests.get(url, headers=header) as response:
            print("status:",response.status_code)
            print("encoding:",response.encoding)
            if (response.status_code == 200):
                return response
        print('get soup works done!')
        return None

    def getMilsTime(self):
        milstime = lambda: int(round(time.time() * 1000))
        return milstime()

# def main():
#     wc = WeatherCrawl()
#     while 1:
#         wc.getWeather()
#         time.sleep(1)
#         break

# if __name__ == '__main__':
#     main()
def getTodayWeather():
    wc = WeatherCrawl()
    wc.getWeather()
#    wc.todayInfo["city"] = wc.city
#    wc.todayInfo["province"] = wc.province
    return wc.todayInfo

def getSevenDayWeather():
    wc = WeatherCrawl()
    wc.getWeather()
    return wc.sevenToday
#getWeatherInfo()