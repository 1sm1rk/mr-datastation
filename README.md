# mr-datastation

## Flow

client -> datastation : get public key 
client -> datastation : get token (encrypted msg with key) 
datastation -> client : send token 
 
client -> datastation : send data with token