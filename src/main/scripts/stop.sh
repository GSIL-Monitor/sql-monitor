#!/bin/bash
pid='/var/run/sql-monitor.pid'

kill -15 `cat $pid`