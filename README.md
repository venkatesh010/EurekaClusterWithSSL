# EurekaClusterWithSSL
This Repository shows demonstration of Eureka Cluster with Spring Cloud Gateway and Dummy Microservice over SSL 


Code Contains:
1) Service Discovery Cluster (Two profiles zone1 and zone2 each running over One Way SSL with Basic Auth for client authentication)
2) Spring Cloud Gateway (Two Profiles zone1 and zone2 running over Two way SSL)
3) DUMMY Microservice (Two Profiles zone1 and zone2 running over Two way SSL)


Only Key Point here is in order to support SSL your SSL Certs should have SAN's as the DNS names configured in it as the RestClients used by eureka are not configurable currently for adding NoOpHostnameVerifier to it.
