sortPerformance<-read.csv("results.csv",header = FALSE);
sortPerformance[,4]<-as.numeric(levels(sortPerformance[,4])[sortPerformance[,4]])

dataSize<-c(10,100,1000)
IR<-subset(sortPerformance, (V3 %in% c("IR")))
MR<-subset(sortPerformance, (V3 %in% c("MR")))
IA<-subset(sortPerformance, (V3 %in% c("IA")))
MA<-subset(sortPerformance, (V3 %in% c("MA")))
ID<-subset(sortPerformance, (V3 %in% c("ID")))
MD<-subset(sortPerformance, (V3 %in% c("MD")))


results<-cbind(IR$V2, IR$V4,MR$V4,IA$V4,MA$V4,ID$V4,MD$V4)
results<-as.data.frame(results)
colnames(results)<-c("size","IR","MR","IA","MA","ID","MD")
plot(x = log10(IR$V2),log10(IR$V4),type="b",col="red",main="Data in Random Order")
lines(x = log10(MR$V2),log10(MR$V4),type="b",col="blue")

plot(x = log10(MA$V2),log10(MA$V4),type="b",col="blue",main="Data in Asceding Order",ylim=c(-10,1))
lines(x = log10(IA$V2),log10(IA$V4),type="b",col="red")

plot(x = log10(ID$V2),log10(ID$V4),type="b",col="red",main="Data in Descending Order")
lines(x = log10(ID$V2),log10(MD$V4),type="b",col="blue")

library(ggplot2)
library("reshape2")

par(mfrow = c(1,3))
ggplot()+
  geom_line(data=results, 
            aes(x=log10(results$size),y=results$IR,
                color ="Insertion Sort")) +
  geom_point(aes(x=log10(c(results$size)),y=results$IR),color="red",shape=19,size=3)+
  geom_line(data = results,
            aes(x = log10(c(results$size)), y = results$MR, 
                color = "Merge Sort"))+
  geom_point(aes(x=log10(c(results$size)),y=results$MR),color="cyan3",shape=19,size=3)+
xlab("log(Data Size)") +
  ylab("Sorting time consumed") +
  ggtitle("Data in Random Order")

ggplot()+
  geom_line(data=results, 
            aes(x=log10(results$size),y=results$IA,
                color ="Insertion Sort")) +
  geom_point(aes(x=log10(c(results$size)),y=results$IA),color="red",shape=19,size=3)+
  geom_line(data = results,
            aes(x = log10(c(results$size)), y = results$MA, 
                color = "Merge Sort"))+
  geom_point(aes(x=log10(c(results$size)),y=results$MA),color="cyan3",shape=19,size=3)+
  xlab("log(Data Size)") +
  ylab("Sorting time consumed") +
  ggtitle("Data in Ascending Order")



ggplot()+
  geom_line(data=results, 
            aes(x=log10(results$size),y=results$ID,
                color ="Insertion Sort")) +
  geom_point(aes(x=log10(c(results$size)),y=results$ID),color="red",shape=19,size=3)+
  geom_line(data = results,
            aes(x = log10(c(results$size)), y = results$MD, 
                color = "Merge Sort"))+
  geom_point(aes(x=log10(c(results$size)),y=results$MD),color="cyan3",shape=19,size=3)+
  xlab("log(Data Size)") +
  ylab("Sorting time consumed") +
  ggtitle("Data in Descending Order")

