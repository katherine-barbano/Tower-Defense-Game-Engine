## Description

Summary of the feature's bug (without describing implementation details)
- levelview height and width dimensions are reversed

## Expected Behavior

Describe the behavior you expect
When the user changes the final SCREEN_HEIGHT value, the height changes.  When the user changes the final SCREEN_WIDTH
value, the width changes.

## Current Behavior

When the user changes the final SCREEN_HEIGHT value, the width changes.  When the user changes the
final SCREEN_WIDTH value, the height changes. 

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. Change the value of Level.SCREEN_HEIGHT
 1. Change the value of Level.SCREEN_WIDTH
 1. Notice that the dimensions of the window are height=SCREEN_WIDTH and width=SCREEN_HEIGHT

## Failure Logs

N/A

## Hypothesis for Fixing the Bug

Write a test to check that the value of the width of the scene is equal to SCREEN_WIDTH, and
write a test to check that the value of the height of the scene is equal to SCREEN_HEIGHT.  Both
will fail.

Change the order of parameters in setupScene method in the Viewable interface and switch the values 
for SCENE_HEIGHT and SCENE_WIDTH in Level.

