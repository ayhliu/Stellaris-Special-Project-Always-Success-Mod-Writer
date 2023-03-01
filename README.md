# Stellaris Special Project Always Success Mod Writer

## Purpose

Stellaris stores all its Special Projects under `common\special_projects\` and they also have a documentation `common\special_projects\documentation.txt`

The mod writer intends to create modified Special Projects to put into a mod that allows player to always complete Special Projects successfully. 

## Approach

I do not know of a simple solution, so I made a brutal approach. In each Special Project, the mod writer copies everything in `on_success = {}`, surrounds it with a `from = {}`, and pastes into `on_fail = {}`.

The `from` is needed because `on_success` works on the project scope defined by `event_scope`, while `on_fail` works on the country scope. The `from` brings us back to project scope. See Stellaris documentation. 

## Operation

Place all Stellaris 3.0.2 Special Projects in `special_projects\i\` and run `SP.java` to generate modified Special Projects in `special_projects\o\`.

## Conditions

The mod writer ignores comments and can handle Special Projects with no `on_fail`.
