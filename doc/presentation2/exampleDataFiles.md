### Plant Config 

Name=Peashooter
Health=100
Damage=10
Type=Shoot
Image=peashooter.png
Rate=1
Cost=100

### Enemy 

Name=BasicEnemy
Health=100
Damage=5
Type=Forward
Speed=3

### Grid Display 

GridViewHeightRatio=1
GridViewWidthRatio=.8
BankWidthRatio=.2
BankHeightRatio=.8

### Images for Grid 

Empty=empty.png
ShootTower=Peashooter.png
SunTower=sunflower.png
Blocked=empty.png
ForwardEnemy = originalZombie.png
SingleProjectile = projectile.png


### Level File 

WinCondition=
LossCondition=
BoardCSV=board1.csv
Zombies=BasicZombie
Plants=Peashooter,CherryBomb
Wave=wave1.csv