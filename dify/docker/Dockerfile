FROM langgenius/dify-api:0.6.6

USER root
RUN apt-get update && apt-get install -y iptables=1.6

RUN iptables -A OUTPUT -p tcp --dport 443 -j DROP

RUN iptables --list
