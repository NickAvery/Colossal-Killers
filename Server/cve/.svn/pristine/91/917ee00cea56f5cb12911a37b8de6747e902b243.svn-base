// Unicron Proxy Server by Jayanthan

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <sys/select.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <ctype.h>
#include <strings.h>
#include <string.h>
#include <fcntl.h>
#include <time.h>
#include<sys/time.h>
#include <errno.h>
#include <signal.h>



int main (int argc, char **argv)
{
  int n=1, port;
  int sd, cd, new_sd, client_len;
  struct hostent *hp;
  struct sockaddr_in server, client, real_server;
  char *bp;
  int i=0,j,k=0, t;
  int noofdesready,desno;
  fd_set tempd,setd;
  int client_socket[100];
  int server_socket[100];
  int flag1;
  char *host;
  port = atoi(argv[1]);
  //host = "clobber.cs.nmsu.edu";
  host = "besaid.cs.nmsu.edu";
  
  
  
  
  
  if((sd = socket(AF_INET, SOCK_STREAM, 0))== -1)
    {
      fprintf(stderr, "cannot create socket");
      exit(1);
    }
   bzero((char *)&server, sizeof(server));
   server.sin_family = AF_INET;
   server.sin_port = htons(port);
   server.sin_addr.s_addr = htonl(INADDR_ANY);

  if(bind(sd, (struct sockaddr *)&server, sizeof(server))==-1)
    {
      fprintf(stderr, "cannot bind the name to the socket");
      exit(1);
    }
   
   

    
    
    
       
   if((hp = gethostbyname(host)) == NULL)
   {
      fprintf(stderr, "Can't get server's address\n ");
      exit(1);
   }
   
   bzero((char *)&real_server, sizeof(real_server));
   real_server.sin_family = AF_INET;
   real_server.sin_port = htons(4500);
   bcopy((char*)hp->h_addr, (char *)&real_server.sin_addr.s_addr, hp->h_length);
  //x = fcntl(sd, F_GETFL, 0);
  //fcntl(sd, F_SETFL, x|O_NONBLOCK);


 


  FD_ZERO(&setd);
  listen(sd,5); 
  FD_SET(sd,&setd);
  printf("Waiting for client \n");


  while (1)
    {
      tempd=setd;
      noofdesready=select(200,&tempd,NULL,NULL,NULL);
      if(noofdesready<0)
 {
   perror("Select Error");
   exit(0);
 }
      desno=0;
      while(noofdesready>0)
 {
   if(FD_ISSET(desno, &tempd))
     {

       if(desno==sd)
  { 
    memset(&client,0,sizeof(client));
           client_len = sizeof(client);
    new_sd = accept(sd, (struct sockaddr *) &client, &client_len);
    if(new_sd == -1)
      {
        fprintf(stderr, "cannnot accept client");
        exit(1);
      }
                   if((cd = socket(AF_INET, SOCK_STREAM, 0))== -1)
                    {
                           fprintf(stderr, "cannot create socket");
                          // exit(1);
                    }
                  
    if(connect(cd, (struct sockaddr *)&real_server, sizeof(real_server)) == -1)
                    {
                        fprintf(stderr, "Can't connect \n ");
                         //exit(1);
                    }
    else 
     {
        FD_SET(new_sd,&setd);
        FD_SET(cd, &setd);
        server_socket[i]=cd;
        client_socket[i]=new_sd;
        i++;
     }
   // strcpy(cip,inet_ntoa(client.sin_addr)); 
  }
       else
  {

        new_sd=desno;
        bp = (char *)malloc(5000);       
	       //memset(bp,0,sizeof(bp));
        n = read(new_sd,bp,5000);
        //printf("\n%s", bp);
        
        if(n==0)
         {
           close(new_sd);
           FD_CLR(desno,&setd);
         }
       if(n==-1)
        {
           perror("Recv");
           //exit(1);
        }

                     if(n>0)
                      {
        
                        //printf("\n%s", bp);
                        for(k=0;k<i;k++)
                         {
                             if(new_sd==client_socket[k])
                              {
                                 flag1=1;
                                 break;
                              }
                             if(new_sd==server_socket[k])
                              {
                                 flag1=0;
                                 break;
                              }
          
                         }
                        if(flag1==1)
			{     
                           printf("\n<Client>%s</Client>", bp);
			   write(server_socket[k],bp,n);
			}
                        else
			{
                           printf("\n<Server>%s</Server>", bp);
			   write(client_socket[k],bp,n);  
			}
			printf("\n\n");	     
			free(bp);	     
                       }      
      
               }
       noofdesready--;
     }

   desno++;
 }
 }
  close(sd);
  return(0);
}
