# DisciTrack User Guide

DisciTrack helps you track todos, deadlines, and events. Type `help` or click **Commands** for the command guide.

## Tagging tasks

Tags are short labels that help distinguish tasks with similar descriptions. Tags work on all task types,
including completed tasks. Different tasks may share the same tag.

### Add tags when creating a task

Append `/tag TAG` for each label, after the description and all dates:

```text
todo sleep /tag personal
todo prepare slides /tag CS2103T /tag school
deadline submit report /by 2026-10-01 /tag CS2103T
event meeting /from 2026-10-01 /to 2026-10-02 /tag project
```

Tags are optional. Existing commands such as `todo sleep` still work. Dates use `yyyy-MM-dd`.

For the first task, `todo sleep /tag personal` returns:

```text
Alright! I've added this task:

[T] [ ] sleep [#personal]
You now have 1 task.

Lock in! Try to finish as soon as possible!
```

### Add or remove a tag later

Use one tag per command and the task's current full-list number:

```text
tag 2 School
untag 2 school
```

For task 2 named `prepare slides`, adding its first tag returns:

```text
Added tag "School" to task 2:
[T] [ ] prepare slides [#School]
```

Removing it returns:

```text
Removed tag "School" from task 2:
[T] [ ] prepare slides
```

Adding an existing tag reports `Task 2 already has tag "School".` Removing an absent tag reports
`Task 2 does not have tag "urgent".` Neither operation changes or saves the task.

### Tag rules

- Use 1–30 English letters, digits, hyphens, or underscores. Start with a letter or digit.
- Valid examples: `CS2103T`, `nus-teacher`, `project_2`, `2026`.
- Spaces, quotation marks, commas, pipes, and a typed `#` prefix are not allowed.
- Names match ignoring case: `School` and `school` are the same tag on one task.
- Keep the spelling first added to each task. There is no fixed limit on the number of tags.
- Show tags in the order added. Removing and re-adding puts a tag last, with the newly supplied spelling.
- Repeated creation tags, such as `/tag School /tag school`, keep one copy, spelled `School`.
- The same label on two different tasks is allowed; duplicate prevention applies only within a task.
- Tagging never changes the description, dates, completion status, or task order.

Invalid commands do not partially change tasks:

| Command | Response |
| --- | --- |
| `tag 2 school urgent` | `UHOH! Use: tag NUMBER TAG` |
| `untag 2` | `UHOH! Use: untag NUMBER TAG` |
| `tag abc school` | `UHOH! Please enter a valid task number!` |
| `todo sleep /tag` | `UHOH! Each /tag must be followed by one tag name.` |
| `todo sleep /tag school urgent` | `UHOH! Put tags at the end using /tag TAG for each tag.` |
| `deadline report /tag school /by 2026-10-01` | `UHOH! Put tags at the end using /tag TAG for each tag.` |
| `tag 2 #school` (task 2 exists) | `UHOH! Tags must be 1-30 characters, start with a letter or digit, and contain only English letters, digits, hyphens, or underscores.` |

A standalone lowercase `/tag` is reserved when creating tasks. `todo explain /tag syntax` creates description
`explain` with tag `syntax`; it does not store `/tag syntax` in the description. There is no escaping or quoting
mechanism for a literal standalone `/tag`. `/tagging` remains ordinary description text.

### Recognise tasks in results

Tags appear after dates, everywhere a task is displayed. For example:

```text
find slides
```

```text
Here are the matching tasks in your list:
3. [T] [ ] prepare slides [#CS2103T]
7. [D] [ ] rehearse slides (by: Oct 01 2026) [#CS2101]
```

Use `tag 7 urgent` to tag the second result. `find` and `checkdate` display the original full-list numbers,
so there may be gaps. Numbers are current list positions, not permanent IDs: deleting a task shifts later
numbers. Earlier chat messages are not updated; search again or use `list` to get the current numbers.

`find` still searches descriptions only, ignoring case. A tag by itself does not make a task match.
Tag-based search, global tag renaming, and deleting tags across all tasks are not included.

### Saving, compatibility, and recovery

Successful tag changes are saved automatically in `data/discitrack.txt`. Existing saved tasks load with no tags.
Untagged lines retain their previous format; tagged lines append an optional `tags=` field:

```text
T | 0 | sleep
T | 0 | prepare slides | tags=CS2103T,school
D | 1 | report | 2026-10-01 | tags=CS2103T
E | 0 | meeting | 2026-10-01 | 2026-10-02 | tags=project
```

Mixed old and new records are supported. Loading alone does not rewrite the file, and existing descriptions
containing `/tag` stay literal. Older app versions may discard tags when saving; use the updated app to retain them.
The existing limitation on using the ` | ` field separator inside descriptions remains.

Malformed tag fields stop the entire load and report the line, for example:

```text
UHOH! Could not load your tasks: invalid tag data on line 3.
Task commands are disabled to protect your saved data. Fix the file and restart.
```

Only `help` and `bye` remain available in this state. Repair the indicated record and restart; the app will not
silently discard tasks or overwrite the affected file. A missing data file starts an empty list, but an unreadable
existing file blocks task commands.

For example, `tags=`, `tags=school,`, and `tags=#school` are invalid. Valid duplicate stored names collapse to the
first spelling. Removing a task's last tag removes its optional tag field on the next successful save.

Saving writes a temporary file beside the data file and atomically replaces the destination. If saving fails for
`tag`, `untag`, or creating a tagged task, the original file and in-memory state are retained:

```text
UHOH! I could not save your tasks. No changes were made.
```

This requires a file system that supports atomic replacement. There is no fallback that overwrites the file
in place. Other existing commands retain their previous in-memory behaviour when a save fails.
