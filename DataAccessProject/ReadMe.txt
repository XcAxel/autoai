数据接入测试工具说明
1，测试整车数据接入测试
2，测试步骤：
1),输入最新车型字典（CHB141,CHB071,CHB073）
2),输入某段时间的原始数据（进行编码的文件），并在原始数据中找到测试车辆VIN。
3),根据时间段在http://120.92.73.87/tass/menu/goMainPage.do进行车况查询，并下载车况数据文件CSV
4),输入测试程序计算结果（All.txt）。
5),车型分类：CHB071-EAD-7DCT-FLA；CHB073-EAD-7DCT-FLA；CH141E-420

3，目录结构：
/Source/BaseInfo/CHB141.txt,CHB0731.txt,typefile.txt    保存字典文件路径，CHB141车型字典需命名为CHB141.txt，CHB073,CHB071车型字典需命名为CHB0731.txt，原始数据文件需命名为typefile.txt
/Source/OriginalData    保存下载时段CSV文件，根据VIN对应车型分别重命名为CHB141.csv,CHB0731.csv     或者.xls文件
/Source/ResultData    保存数据接入后台程序计算结果并重命名为all.txt    同时程序根据所挑选的VIN进行数据筛选，并将目标数据保存在此路径下的filterRes.txt
/Source/ResultLog    保存程序运行计算结果文件Normallog.csv，以及问题日志Errlog.csv

4，Normallog表头：LineNumber,KeyName,ReportTime,OriginalValue,Factor,Offset,Cal-res,Prgm-res,Differ,DifferSign
	Errlog表头：LineNumber,ReportTime,KeyName,Reason
5，字典解析：measure误差（可调），head标识表头，格式均为key=value   字典key-value格式：key=factor,offset

6，程序入口：main.java,mainForXls.java
main.java为计算csv数据使用。
mainForXls.java为计算excel数据使用。
csv保存成excel会造成列丢失。