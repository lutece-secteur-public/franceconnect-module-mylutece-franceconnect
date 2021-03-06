
#Module MyLutece pour FranceConnect

![](http://dev.lutece.paris.fr/plugins/module-mylutece-franceconnect/images/franceconnect.png)

##Introduction

Ce module s'appuie sur le [plugin FranceConnect](https://github.com/lutece-platform/lutece-auth-plugin-franceconnect.git) pour réaliser une authentification MyLutece basée sur un fournisseur d'identités de la plate-forme FranceConnect.

#Installation

##Configuration

Vérifier dans le fichier `WEB-INF/conf/plugins/mylutece.properties` que l'authentification est bien activée comme suit :


```

# Enable authentication
mylutece.authentication.enable=true
    
mylutece.authentication.class=fr.paris.lutece.plugins.mylutece.modules.franceconnect.authentication.FranceConnectAuthentication

mylutece.url.login.page=Portal.jsp?page=franceconnect
mylutece.url.doLogout=servlet/plugins/mylutece/modules/franceconnect/logout
mylutece.url.default.redirect=../../Portal.jsp


```


##Usage

La page d'authentification FranceConnect s'appelle à partir de l'URL suivante :

 `http://myhost/lutece/jsp/site/Portal.jsp?page=franceconnect` 

Il est possible de réaliser ce formulaire d'authentification dans un portlet, soit en copiant le contenu du formulaire dans un portlet HTML, soit en modifiant la feuillede style XSL du portlet MyLutece.

##Dépannage


 
* Vérifiez bien la configuration de MyLutece comme indiqué ci-dessus.
* Assurez-vous que le module FranceConnect est le seul module MyLutece présent dans la Webapp. Il ne doit pas y avoir d'autres fichiers `mylutece-xxxxx.properties` dans le répertoire `WEB-INF/conf/plugins/` .
* Vérifiez bien la configuration du plugin FranceConnect.
* L'activation des logs en mode debug se fait en ajoutant la ligne suivante dans le fichier `WEB-INF/conf/config.properties` dans la rubrique LOGGERS :

```

log4j.logger.lutece.franceconnect=DEBUG, Console

```





[Maven documentation and reports](http://dev.lutece.paris.fr/plugins/module-mylutece-franceconnect/)



 *generated by [xdoc2md](https://github.com/lutece-platform/tools-maven-xdoc2md-plugin) - do not edit directly.*