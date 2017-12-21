# Organicer

![alt text](https://i.imgur.com/3DxieTj.jpg)


## New Features!

  - Pull Repository
  - Push to Repository
  
> Dadurch, dass man fehlende Lernunterlagen hat, kann man schwer für Tests oder etwaige Sachen lernen. Die Unterlagen werden > durch Social-Media Plattformen geteilt, sind jedoch nicht übersichtlich gegenzeichnet und damit verliert man viel Zeit beim > Suchen oder es wird sogar nichts gefunden. Die Organisation funktioniert, aber ist jedoch sehr mühsam und leicht zum > > > > unterschätzen bzw. zu übersehen.Ziel ist es daher mit dem Organicer die Planung innerhalb des Schuljahres zu verbessern und > eine Möglichkeit zu entwickeln um das Sortieren, Filtern und Suchen der Schulunterlagen zu erleichtern um somit die Zeit > > fürs Lernen zu investieren zu können. 


### Navigate to your working directory(where the .git file is located)
#### Actual Branch is : Oktober!

### HowTo : Pull

```sh
$ git checkout branch
$ git pull origin branch
```

### HowTo : Push

```sh
$ git checkout branch
$ git add .
$ git commit -m "What have been added/deletet?"
$ git push origin branch
```

### HowTo : Merge
#### For example: You work in buchinger branch and commit something that finally works, you have to merge it to the actual Branch. Not to master! Change the branch to the actual branch und merge your branch with the actual branch as you see in the following example.

```sh
$ git checkout actualBranch
$ git merge branch
```

