#!/bin/bash

pass=0
fail=0

pass=`expr $pass + 1`

pass=`expr $pass + 1`
pass=`expr $pass + 1`
pass=`expr $pass + 1`
pass=`expr $pass + 1`

echo "pass=$pass, fail=$fail"

while :; do
  let count=count+1
  echo "This is the $count sleep"
  sleep 3
done

